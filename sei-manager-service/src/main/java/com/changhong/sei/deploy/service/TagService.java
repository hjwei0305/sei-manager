package com.changhong.sei.deploy.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.dto.serach.SearchFilter;
import com.changhong.sei.core.dto.serach.SearchOrder;
import com.changhong.sei.core.limiter.support.lock.SeiLock;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.deploy.dao.TagDao;
import com.changhong.sei.deploy.dto.TagDto;
import com.changhong.sei.deploy.entity.AppModule;
import com.changhong.sei.deploy.entity.Tag;
import com.changhong.sei.integrated.service.GitlabService;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


/**
 * 应用标签(Tag)业务逻辑实现类
 *
 * @author sei
 * @since 2020-11-23 08:34:09
 */
@Service("tagService")
public class TagService extends BaseEntityService<Tag> {
    private static final Logger LOG = LoggerFactory.getLogger(TagService.class);

    @Autowired
    private TagDao dao;

    @Autowired
    private AppModuleService moduleService;

    @Autowired
    private GitlabService gitlabService;

    @Override
    protected BaseEntityDao<Tag> getDao() {
        return dao;
    }

    /**
     * 获取项目分支
     *
     * @param gitId git项目id
     * @return 项目分支列表
     */
    public ResultData<List<String>> getProjectBranches(String gitId) {
        AppModule module = moduleService.getAppModuleByGitId(gitId);
        if (Objects.isNull(module)) {
            return ResultData.fail("应用模块gitId[" + gitId + "]不存在.");
        }
        return gitlabService.getProjectBranches(gitId);
    }

    /**
     * 获取最新的标签
     *
     * @param moduleId 模块id
     * @return 创建结果
     */
    public ResultData<TagDto> getLastTag(String moduleId) {
        AppModule module = moduleService.findOne(moduleId);
        if (Objects.isNull(module)) {
            return ResultData.fail("应用模块Id[" + moduleId + "]不存在.");
        }
        TagDto dto;
        Search search = Search.createSearch();
        search.addFilter(new SearchFilter(Tag.FIELD_MODULE_ID, moduleId));
        search.addSortOrder(new SearchOrder(Tag.FIELD_MAJOR, SearchOrder.Direction.DESC));
        search.addSortOrder(new SearchOrder(Tag.FIELD_MINOR, SearchOrder.Direction.DESC));
        search.addSortOrder(new SearchOrder(Tag.FIELD_REVISED, SearchOrder.Direction.DESC));
        Tag tag = dao.findFirstByFilters(search);
        if (Objects.nonNull(tag)) {
            dto = convert(tag);
            dto.setId(null);
            dto.setMessage(null);
        } else {
            dto = new TagDto();
            String version = module.getVersion();
            if (StringUtils.isNotBlank(version)) {
                String major = version.split("\\.")[0];
                if (StringUtils.isNumeric(major)) {
                    dto.setMajor(Integer.valueOf(major));
                }
            }

        }
        return ResultData.success(dto);
    }

    /**
     * 获取最新的标签
     *
     * @param id id
     * @return 创建结果
     */
    public ResultData<TagDto> getTag(String id) {
        Tag tag = dao.findOne(id);
        if (Objects.isNull(tag)) {
            return ResultData.fail("标签[" + id + "]不存在");
        }

        return ResultData.success(convert(tag));
    }

    /**
     * 获取项目标签
     *
     * @param gitId gitId
     * @return 创建结果
     */
    public ResultData<List<TagDto>> getTags(String gitId) {
        AppModule appModule = moduleService.getAppModuleByGitId(gitId);
        if (Objects.isNull(appModule)) {
            return ResultData.fail("未找到git[" + gitId + "]对应的应用模块.");
        }
        Search search = Search.createSearch();
        search.addFilter(new SearchFilter(Tag.FIELD_MODULE_ID, appModule.getId()));
        search.addSortOrder(new SearchOrder(Tag.FIELD_MAJOR, SearchOrder.Direction.DESC));
        search.addSortOrder(new SearchOrder(Tag.FIELD_MINOR, SearchOrder.Direction.DESC));
        search.addSortOrder(new SearchOrder(Tag.FIELD_REVISED, SearchOrder.Direction.DESC));
        List<Tag> tags = dao.findByFilters(search);
        if (CollectionUtils.isNotEmpty(tags)) {
            return ResultData.success(tags.stream().map(this::convert).collect(Collectors.toList()));
        } else {
            return ResultData.success(Lists.newArrayList());
        }
    }

    /**
     * 创建标签
     *
     * @param request 创建标签请求
     * @return 创建结果
     */
    public ResultData<Void> createTag(TagDto request) {
        AppModule module = moduleService.findByProperty(AppModule.ID, request.getModuleId());
        if (Objects.isNull(module)) {
            return ResultData.fail("应用模块id[" + request.getModuleId() + "]不存在.");
        }
        // 检查tag是否存在(模块代码+tag唯一)
        String tagName = request.getTagName();
        Search search = Search.createSearch();
        search.addFilter(new SearchFilter(Tag.FIELD_MODULE_ID, module.getId()));
        search.addFilter(new SearchFilter(Tag.FIELD_CODE, tagName));
        Tag existTag = dao.findFirstByFilters(search);
        if (Objects.nonNull(existTag)) {
            return ResultData.fail("模块[" + module.getCode() + "]已存在Tag[" + tagName + "].");
        }

        Tag tag = new Tag();
        tag.setModuleId(module.getId());
        tag.setModuleCode(module.getCode());
        tag.setBranch(request.getBranch());
        tag.setMajor(request.getMajor());
        tag.setMinor(request.getMinor());
        tag.setRevised(request.getRevised());
        tag.setMessage(request.getMessage());

        org.gitlab4j.api.models.Tag gitTag;
        ResultData<org.gitlab4j.api.models.Tag> resultData = gitlabService.getProjectTag(module.getGitId(), tag.getTagName());
        if (resultData.successful()) {
            gitTag = resultData.getData();
            tag.setRelease(Objects.nonNull(tag.getRelease()));
            tag.setCommitId(gitTag.getCommit().getId());
            tag.setMessage(gitTag.getMessage());
            tag.setCreateTime(gitTag.getCommit().getCreatedAt().getTime());
            tag.setCreateAccount(gitTag.getCommit().getAuthorName());

            tag.setCode(tag.getTagName());
            this.save(tag);
        } else {
            resultData = gitlabService.createProjectTag(module.getGitId(), tag.getTagName(), tag.getBranch(), tag.getMessage());
            if (resultData.successful()) {
                gitTag = resultData.getData();
                tag.setRelease(Objects.nonNull(tag.getRelease()));
                tag.setCommitId(gitTag.getCommit().getId());
                tag.setCreateTime(gitTag.getCommit().getCreatedAt().getTime());
                tag.setCreateAccount(gitTag.getCommit().getAuthorName());

                tag.setCode(tag.getTagName());
                this.save(tag);
            } else {
                return ResultData.fail(resultData.getMessage());
            }
        }
        return ResultData.success();
    }

    /**
     * 删除项目标签
     *
     * @param id id
     * @return 创建结果
     */
    public ResultData<Void> deleteTag(String id) {
        Tag tag = dao.findOne(id);
        if (Objects.isNull(tag)) {
            return ResultData.fail("标签[" + id + "]不存在");
        }
        AppModule module = moduleService.findOne(tag.getModuleId());
        if (Objects.isNull(module)) {
            return ResultData.fail("应用模块[" + tag.getModuleCode() + "]不存在.");
        }
        ResultData<Void> resultData = gitlabService.deleteProjectTag(module.getGitId(), tag.getCode());
        if (resultData.successful()) {
            this.delete(id);
        }
        return ResultData.success();
    }


    private static final Pattern RULE_PATTERN = Pattern.compile("^[1-9]\\d{0,1}\\.(\\d){1,3}\\.(\\d){1,4}$");
    private static final Pattern FIND_PATTERN = Pattern.compile("(\\d+)(?:\\.)(\\d+)(?:\\.)(\\d+)");

    /**
     * 同步gitlab项目标签
     *
     * @param moduleId 模块id
     * @return 同步结果
     */
    @SeiLock(key = "'manager:syncTag' + #moduleId", fallback = "syncTagFallback")
    @Transactional(rollbackFor = Exception.class)
    public ResultData<Void> syncTag(String moduleId) {
        AppModule module = moduleService.findOne(moduleId);
        if (Objects.isNull(module)) {
            return ResultData.fail("应用模块id[" + moduleId + "]不存在.");
        }

        Set<String> tagNameSet;
        // 获取所有tag
        List<Tag> allTags = dao.findListByProperty(Tag.FIELD_MODULE_ID, moduleId);
        if (CollectionUtils.isNotEmpty(allTags)) {
            tagNameSet = allTags.stream().map(Tag::getCode).collect(Collectors.toSet());
            allTags.clear();
        } else {
            tagNameSet = new HashSet<>();
        }
        List<Tag> tagList = new ArrayList<>(16);
        ResultData<List<org.gitlab4j.api.models.Tag>> resultData = gitlabService.getProjectTags(module.getGitId());
        if (resultData.successful()) {
            Tag tag;
            List<org.gitlab4j.api.models.Tag> tags = resultData.getData();
            for (org.gitlab4j.api.models.Tag gitTag : tags) {
                String version = gitTag.getName();
                if (StringUtils.isBlank(version) || tagNameSet.contains(version)) {
                    continue;
                }

                // 检查是否符合版本规则
                if (!RULE_PATTERN.matcher(version).matches()) {
                    continue;
                }

                Matcher m = FIND_PATTERN.matcher(version);
                if (m.find()) {
                    tag = new Tag();
                    tag.setModuleId(moduleId);
                    tag.setModuleCode(module.getCode());
                    tag.setMajor(Integer.valueOf(m.group(1)));
                    tag.setMinor(Integer.valueOf(m.group(2)));
                    tag.setRevised(Integer.valueOf(m.group(3)));
                    tag.setCode(tag.getTagName());
                    tag.setMessage(gitTag.getMessage());

                    tag.setRelease(Objects.nonNull(tag.getRelease()));
                    tag.setCommitId(gitTag.getCommit().getId());
                    tag.setMessage(gitTag.getMessage());
                    tag.setCreateTime(gitTag.getCommit().getCreatedAt().getTime());
                    tag.setCreateAccount(gitTag.getCommit().getAuthorName());

                    tagList.add(tag);
                }
            }
            if (CollectionUtils.isNotEmpty(tagList)) {
                this.save(tagList);
            }
        }
        return ResultData.success();
    }

    /**
     * 同步标签分布式锁降级处理方法
     *
     * @SeiLock(key = "'syncTag' + #moduleId", fallback = "syncTagFallback")
     */
    public ResultData<Void> syncTagFallback(String moduleId) {
        return ResultData.fail("正在同步[" + moduleId + "]的gitlab标签, 请稍后再试...");
    }

    private TagDto convert(Tag tag) {
        TagDto dto = new TagDto();
        if (Objects.nonNull(tag)) {
            dto.setModuleId(tag.getModuleId());
            dto.setModuleCode(tag.getModuleCode());
            dto.setTagName(tag.getTagName());
            dto.setMajor(tag.getMajor());
            dto.setMinor(tag.getMinor());
            dto.setRevised(tag.getRevised());
            dto.setRelease(tag.getRelease());
            dto.setCommitId(tag.getCommitId());
            dto.setMessage(tag.getMessage());
            dto.setCreateTime(tag.getCreateTime());
            dto.setCreateAccount(tag.getCreateAccount());
        }
        return dto;
    }
}