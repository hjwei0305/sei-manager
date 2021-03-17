package com.changhong.sei.cicd.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.common.Constants;
import com.changhong.sei.cicd.dao.DeployTemplateDao;
import com.changhong.sei.cicd.dto.DeployStageParamDto;
import com.changhong.sei.cicd.dto.DeployTemplateStageResponse;
import com.changhong.sei.cicd.dto.TemplateType;
import com.changhong.sei.cicd.entity.DeployConfig;
import com.changhong.sei.cicd.entity.DeployTemplate;
import com.changhong.sei.integrated.service.JenkinsService;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 部署模板(DeployTemplate)业务逻辑实现类
 *
 * @author sei
 * @since 2020-11-23 08:34:03
 */
@Service("deployTemplateService")
public class DeployTemplateService extends BaseEntityService<DeployTemplate> {
    private static final Logger LOG = LoggerFactory.getLogger(DeployTemplateService.class);
    @Autowired
    private DeployTemplateDao dao;
    @Autowired
    private DeployTemplateStageService templateStageService;
    @Autowired
    private DeployConfigService deployConfigService;
    @Autowired
    private JenkinsService jenkinsService;

    @Override
    protected BaseEntityDao<DeployTemplate> getDao() {
        return dao;
    }

    /**
     * 删除数据保存数据之前额外操作回调方法 子类根据需要覆写添加逻辑即可
     *
     * @param id 待删除数据对象主键
     */
    @Override
    protected OperateResult preDelete(String id) {
        // 检查状态控制删除
        DeployTemplate app = this.findOne(id);
        if (Objects.isNull(app)) {
            return OperateResult.operationFailure("[" + id + "]模版不存在,删除失败!");
        }
        if (deployConfigService.isExistsByProperty(DeployConfig.FIELD_TEMP_ID, id)) {
            return OperateResult.operationFailure("[" + id + "]模版已被部署配置使用,不允许删除!");
        }
        return super.preDelete(id);
    }

    /**
     * 按类型获取模版,主要用于Jenkins构建时检查发布类模版是否存在
     *
     * @param type 类型
     * @return 结果
     */
    public ResultData<DeployTemplate> getPublishTemplate(String type) {
        DeployTemplate template = dao.findFirstByProperty(DeployTemplate.FIELD_TYPE, type);
        if (Objects.nonNull(template)) {
            return ResultData.success(template);
        } else {
            return ResultData.fail("未找到[" + type + "]类型的模版");
        }
    }

    /**
     * 同步Jenkins任务
     *
     * @param id 模版id
     * @return 返回结果
     */
    public ResultData<Void> syncJenkinsJob(String id) {
        DeployTemplate template = findOne(id);
        if (Objects.isNull(template)) {
            return ResultData.fail("模版[" + id + "]不存在");
        }

        if (StringUtils.equals(TemplateType.DEPLOY.name(), template.getType())) {
            return ResultData.fail("模版[" + template.getName() + "]不是发版模版");
        }

        // 同步Jenkins任务
        // 任务名
        String jobName = template.getName();
        ResultData<String> xmlResult = this.generateJobXml(template.getId());
        if (xmlResult.failed()) {
            return ResultData.fail(xmlResult.getMessage());
        }
        // 创建Jenkins任务
        String jobXml = xmlResult.getData();
        try {
            boolean exist = jenkinsService.checkJobExist(jobName);
            if (!exist) {
                // 创建Jenkins任务
                return jenkinsService.createJob(jobName, jobXml);
            } else {
                // 修改Jenkins任务
                return jenkinsService.updateJob(jobName, jobXml);
            }
        } catch (Exception e) {
            LOG.error("同步Jenkins任务异常", e);
            return ResultData.fail(e.getMessage());
        }
    }

    /**
     * 生成默认预制参数的Jenkins任务xml
     */
    public ResultData<String> generateJobXml(String templateId) {
        return generateJobXml(templateId, Constants.DEFAULT_STAGE_PARAMS);
    }

    /**
     * 生成自定义参数的Jenkins任务xml
     */
    public ResultData<String> generateJobXml(String templateId, List<DeployStageParamDto> stageParams) {
        DeployTemplate template = this.findOne(templateId);
        if (Objects.isNull(template)) {
            return ResultData.fail("模版不存在.");
        }
        ResultData<List<DeployTemplateStageResponse>> resultData = templateStageService.getStageByTemplateId(templateId);
        if (resultData.failed()) {
            return ResultData.fail(resultData.getMessage());
        }
        StringBuilder script = new StringBuilder();
        script.append("\n\r node {\n\r");

        String str = template.getGlobalParam();
        if (StringUtils.isNotBlank(str)) {
            Map<String, Object> params = JsonUtils.fromJson(str, HashMap.class);
            if (params != null && params.size() > 0) {
                script.append("\n\r // 全局参数 start  \n\r");
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    script.append(" def ").append(entry.getKey()).append(" = \"").append(entry.getValue()).append("\" \n\r");
                }
                script.append("\n\r // 全局参数 end  \n\r");
            }
        }
        List<DeployTemplateStageResponse> templateStages = resultData.getData();
        for (DeployTemplateStageResponse templateStage : templateStages) {
            script.append("stage('").append(templateStage.getName()).append("') { \n\r");
            script.append(templateStage.getPlayscript()).append("\n\r } \n\r");
        }
        script.append("\n\r} \n\r");

        return generateXml(script.toString(), stageParams);
    }

    /**
     * 生成xml方法
     */
    private ResultData<String> generateXml(String scriptStr, List<DeployStageParamDto> stageParams) {
        // 创建document对象
        Document document = DocumentHelper.createDocument();
        // 创建根节点
        Element root = document.addElement("flow-definition");
        // 向根节点添加plugin属性
        root.addAttribute("plugin", "workflow-job@2.39");
        // 生成子节点及子节点内容
        root.addElement("actions");
        root.addElement("description");

        root.addElement("keepDependencies").setText("false");

        Element properties = root.addElement("properties");
        Element gitlab = properties.addElement("com.dabsquared.gitlabjenkins.connection.GitLabConnectionProperty");
        gitlab.addAttribute("plugin", "gitlab-plugin@1.5.13");
        gitlab.addElement("gitLabConnection").addText("rddgit");

        Element hudson = properties.addElement("hudson.model.ParametersDefinitionProperty");
        Element parameterDefinitions = hudson.addElement("parameterDefinitions");
        Element parameter;
        for (DeployStageParamDto stageParam : stageParams) {
            parameter = parameterDefinitions.addElement("hudson.model.StringParameterDefinition");
            parameter.addElement("name").addText(stageParam.getCode());
            parameter.addElement("description").addText(stageParam.getName());
            parameter.addElement("defaultValue").addText(stageParam.getValue());
            parameter.addElement("trim").addText(String.valueOf(stageParam.getTrim()));
        }

        Element definition = root.addElement("definition");
        definition.addAttribute("class", "org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition");
        definition.addAttribute("plugin", "workflow-cps@2.80");

        definition.addElement("script");
//        Element script = definition.addElement("script");
//        script.addText(scriptStr);

        definition.addElement("sandbox").addText("true");

        root.addElement("triggers");
        root.addElement("disabled").addText("false");

        // 设置生成xml的格式
        OutputFormat format = OutputFormat.createPrettyPrint();
        // 设置编码格式
        format.setEncoding("UTF-8");

        // 生成xml
        StringWriter strWriter = new StringWriter();
        XMLWriter xmlWriter = new XMLWriter(strWriter, format);
        try {
            // 设置是否转义，默认使用转义字符
            xmlWriter.setEscapeText(false);
            xmlWriter.write(document);
        } catch (IOException e) {
            LOG.error("生成xml失败", e);
            return ResultData.fail("生成xml失败");
        } finally {
            try {
                xmlWriter.close();
            } catch (IOException ignored) {
            }
            try {
                strWriter.close();
            } catch (IOException ignored) {
            }
        }

//        return ResultData.success(strWriter.toString());
        return ResultData.success(strWriter.toString().replace("<script/>", "<script>".concat(scriptStr).concat("</script>")));
    }
}