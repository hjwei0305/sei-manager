package com.changhong.sei.datamodel.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.dto.serach.SearchFilter;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.service.bo.OperateResultWithData;
import com.changhong.sei.datamodel.dao.DataModelDao;
import com.changhong.sei.datamodel.entity.DataModel;
import com.changhong.sei.datamodel.entity.DataModelField;
import com.changhong.sei.datamodel.entity.DataSource;
import com.changhong.sei.datamodel.manager.DatabaseManager;
import com.changhong.sei.exception.ServiceException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 数据模型(DataModel)业务逻辑实现类
 *
 * @author sei
 * @since 2020-07-28 17:31:41
 */
@Service("dataModelService")
public class DataModelService extends BaseEntityService<DataModel> {
    @Autowired
    private DataModelDao dao;
    @Autowired
    private DataModelFieldService fieldService;
    @Autowired
    private DataSourceService dataSourceService;
//    @Autowired
    private DatabaseManager databaseManager;

    @Override
    protected BaseEntityDao<DataModel> getDao() {
        return dao;
    }

    /**
     * 根据模型类型代码查询数据模型
     *
     * @param modelTypeCode 模型类型代码
     * @return 返回指定类型的数据模型集合
     */
    public List<DataModel> getDataModelByTypeCode(String modelTypeCode) {
        if (StringUtils.isNotBlank(modelTypeCode)) {
            return dao.findByModelTypeCode(modelTypeCode);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 根据数据模型id获取模型字段清单
     *
     * @param modelId 数据模型id
     * @return 返回自定的模型字段清单
     */
    public List<DataModelField> getDataModelFields(String modelId) {
        return fieldService.findByDataModelId(modelId);
    }

    /**
     * 数据保存操作
     *
     * @param entity 模型
     */
    @Override
    public OperateResultWithData<DataModel> save(DataModel entity) {
        OperateResultWithData<DataModel> result = super.save(entity);
        if (result.successful()) {
            DataModel dataModel = result.getData();
            // 模型创建成功,默认添加id字段;
            DataModelField idField = new DataModelField();
            idField.setDataModelId(dataModel.getId());
            idField.setFieldName("id");
            idField.setRemark("主键id");
            // {@link init_data.sql}
            idField.setDataType("IdOrKey");
            idField.setDataTypeDesc("ID标识号");
            idField.setDataLength(36);
            idField.setPrimaryKey(Boolean.TRUE);
            idField.setNotNull(Boolean.TRUE);

            OperateResultWithData<DataModelField> idResult = fieldService.save(idField);
            if (idResult.notSuccessful()) {
                // 回滚数据模型save事务
                throw new ServiceException(idResult.getMessage());
            }
        }
        return result;
    }

    /**
     * 添加默认审计字段
     *
     * @param modelId 数据模型id
     * @return 返回操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultData<String> addAuditFields(String modelId) {
        if (StringUtils.isNotEmpty(modelId)) {
            // 检查是否已有审计字段
            ResultData<String> checkResult = checkExistFieldName(modelId, "creator_id", null);
            if (checkResult.failed()) {
                return checkResult;
            }

            DataModelField modelField;
            List<DataModelField> fields = new LinkedList<>();
            modelField = new DataModelField();
            modelField.setDataModelId(modelId);
            modelField.setFieldName("creator_id");
            modelField.setRemark("创建者");
            // {@link init_data.sql}
            modelField.setDataType("ShortString");
            modelField.setDataTypeDesc("字串-短");
            modelField.setDataLength(56);
            fields.add(modelField);

            modelField = new DataModelField();
            modelField.setDataModelId(modelId);
            modelField.setFieldName("creator_account");
            modelField.setRemark("创建者账号");
            // {@link init_data.sql}
            modelField.setDataType("ShortString");
            modelField.setDataTypeDesc("字串-短");
            modelField.setDataLength(56);
            fields.add(modelField);

            modelField = new DataModelField();
            modelField.setDataModelId(modelId);
            modelField.setFieldName("creator_name");
            modelField.setRemark("创建者名称");
            // {@link init_data.sql}
            modelField.setDataType("ShortString");
            modelField.setDataTypeDesc("字串-短");
            modelField.setDataLength(56);
            fields.add(modelField);

            modelField = new DataModelField();
            modelField.setDataModelId(modelId);
            modelField.setFieldName("created_date");
            modelField.setRemark("创建时间");
            // {@link init_data.sql}
            modelField.setDataType("DateTime");
            modelField.setDataTypeDesc("日期时间");
            fields.add(modelField);


            modelField = new DataModelField();
            modelField.setDataModelId(modelId);
            modelField.setFieldName("last_editor_id");
            modelField.setRemark("创建者");
            // {@link init_data.sql}
            modelField.setDataType("ShortString");
            modelField.setDataTypeDesc("字串-短");
            modelField.setDataLength(56);
            fields.add(modelField);

            modelField = new DataModelField();
            modelField.setDataModelId(modelId);
            modelField.setFieldName("last_editor_account");
            modelField.setRemark("创建者账号");
            // {@link init_data.sql}
            modelField.setDataType("ShortString");
            modelField.setDataTypeDesc("字串-短");
            modelField.setDataLength(56);
            fields.add(modelField);

            modelField = new DataModelField();
            modelField.setDataModelId(modelId);
            modelField.setFieldName("last_editor_name");
            modelField.setRemark("创建者名称");
            // {@link init_data.sql}
            modelField.setDataType("ShortString");
            modelField.setDataTypeDesc("字串-短");
            modelField.setDataLength(56);
            fields.add(modelField);

            modelField = new DataModelField();
            modelField.setDataModelId(modelId);
            modelField.setFieldName("last_edited_date");
            modelField.setRemark("创建时间");
            // {@link init_data.sql}
            modelField.setDataType("DateTime");
            modelField.setDataTypeDesc("日期时间");
            fields.add(modelField);

            fieldService.save(fields);
            return ResultData.success("ok");
        }
        return ResultData.fail("数据模型id不能为空.");
    }

    /**
     * 批量添加数据模型字段
     *
     * @param fields 数据模型字段集合
     * @return 返回操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultData<String> saveModelFields(List<DataModelField> fields) {
        if (CollectionUtils.isEmpty(fields)) {
            return ResultData.fail("数据模型字段添加失败,参数不能为空.");
        }

        // 按数据模型id分组
        Map<String, List<DataModelField>> dataMap = fields.parallelStream()
                .collect(Collectors.groupingBy(DataModelField::getDataModelId));

        ResultData<String> resultData;
        for (Map.Entry<String, List<DataModelField>> entry : dataMap.entrySet()) {
            resultData = saveFields(entry.getKey(), entry.getValue());
            if (resultData.failed()) {
                throw new ServiceException(resultData.getMessage());
            }
        }
        return ResultData.success("ok");
    }

    /**
     * 按数据模型批量保存模型字段
     *
     * @param dataModelId 数据模型id
     * @param fields      模型字段
     * @return 返回操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultData<String> saveFields(String dataModelId, List<DataModelField> fields) {
        if (StringUtils.isBlank(dataModelId)) {
            return ResultData.fail("数据模型id不能为空.");
        }
        if (CollectionUtils.isEmpty(fields)) {
            return ResultData.fail("模型字段不能为空.");
        }

        // 检查是否有重复的字段
        Map<String, Long> dataMap = fields.parallelStream()
                .collect(Collectors.groupingBy(DataModelField::getFieldName, Collectors.counting()));
        if (fields.size() != dataMap.size()) {
            for (Map.Entry<String, Long> entry : dataMap.entrySet()) {
                if (entry.getValue() > 1) {
                    return ResultData.fail("存在重复字段: [" + entry.getKey() + "]");
                }
            }
        }

        DataModel dataModel = dao.findOne(dataModelId);
        if (Objects.isNull(dataModel)) {
            return ResultData.fail("未找到对应的数据模型 id = [" + dataModelId + "]");
        }

        // 删除原有字段
        fieldService.deleteByDataModelId(dataModelId);
        // 添加新字段
        fieldService.save(fields);

        return ResultData.success("ok");
    }


    /**
     * 保存单个模型字段
     *
     * @param field 模型字段
     * @return 返回操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultData<String> saveField(DataModelField field) {
        if (Objects.isNull(field)) {
            return ResultData.fail("保存数据模型字段错误,参数不能为空");
        }

        ResultData<String> checkResult = checkExistFieldName(field.getDataModelId(), field.getFieldName(), field.getId());
        if (checkResult.failed()) {
            return checkResult;
        }

        fieldService.save(field);
        return ResultData.success("ok");
    }

    /**
     * 删除数据模型字段
     *
     * @param fieldIds 删除的数据模型字段id清单
     * @return 返回操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultData<String> deleteModelFields(List<String> fieldIds) {
        if (CollectionUtils.isNotEmpty(fieldIds)) {
            fieldService.delete(fieldIds);
            return ResultData.success("ok");
        } else {
            return ResultData.fail("参数不能为空.");
        }
    }

    public ResultData<String> syncDatabase(List<String> dataModelIds) {
        List<DataModel> dataModels = findByIds(dataModelIds);
        if (CollectionUtils.isNotEmpty(dataModels)) {
            for (DataModel dataModel : dataModels) {
                syncDatabase(dataModel);
            }
        }
        return ResultData.success("ok");
    }

    public ResultData<String> syncDatabase(DataModel dataModel) {
        DataSource dataSource = dataSourceService.findOne(dataModel.getDsId());
        if (Objects.isNull(dataSource)) {
            return ResultData.fail("未找到数据源配置[" + dataModel.getDsName() + "]");
        }

        List<DataModelField> fields = getDataModelFields(dataModel.getId());

        return databaseManager.generateScript(dataSource, dataModel, fields);
    }

    /**
     * 检查字段是否存在
     *
     * @param dataModelId 数据模型id
     * @param fieldName   字段名
     * @param ownerId     所有者id
     * @return 返回检查结果
     */
    private ResultData<String> checkExistFieldName(String dataModelId, String fieldName, String ownerId) {
        Search search = Search.createSearch();
        search.addFilter(new SearchFilter(DataModelField.FIELD_DATA_MODEL_ID, dataModelId));
        search.addFilter(new SearchFilter(DataModelField.FIELD_FIELD_NAME, fieldName));
        if (StringUtils.isNotBlank(ownerId)) {
            search.addFilter(new SearchFilter(DataModelField.ID, ownerId, SearchFilter.Operator.NE));
        }
        long count = fieldService.count(search);
        if (count > 0) {
            return ResultData.fail("存在重复字段: [" + fieldName + "]");
        } else {
            return ResultData.success("ok");
        }
    }
}