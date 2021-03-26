package com.changhong.sei.ge.service;

import com.changhong.sei.common.ObjectType;
import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.dao.BaseTreeDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseTreeService;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.core.service.bo.OperateResultWithData;
import com.changhong.sei.ge.dao.ProjectGroupDao;
import com.changhong.sei.ge.entity.Application;
import com.changhong.sei.ge.entity.ProjectGroup;
import com.changhong.sei.integrated.service.GitlabService;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.gitlab4j.api.models.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;
import java.util.stream.Collectors;


/**
 * 项目组业务逻辑实现类
 *
 * @author sei
 * @since 2020-11-10 16:24:33
 */
@Service
public class ProjectGroupService extends BaseTreeService<ProjectGroup> {

    @Autowired
    private ProjectGroupDao dao;
    @Autowired
    private GitlabService gitlabService;
    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private ProjectUserService projectUserService;

    @Override
    protected BaseTreeDao<ProjectGroup> getDao() {
        return dao;
    }

    /**
     * 移动节点
     *
     * @param nodeId         当前节点ID
     * @param targetParentId 目标父节点ID
     * @return 返回操作结果对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OperateResult move(String nodeId, String targetParentId) {
        return OperateResult.operationFailure("该功能不支持移动");
//        return super.move(nodeId, targetParentId);
    }

    /**
     * 获取整个Menu多根树的树形对象
     *
     * @return Menu多根树对象集合
     */
    public List<ProjectGroup> getProjectGroupTree() {
        List<ProjectGroup> rootTree = getAllRootNode();
        List<ProjectGroup> rootMenuTree = new ArrayList<>();
        for (ProjectGroup aRootTree : rootTree) {
            ProjectGroup menu = getTree(aRootTree.getId());
            rootMenuTree.add(menu);
        }
        return rootMenuTree;
    }

    /**
     * 获取名称匹配的菜单
     *
     * @param name 名称
     */
    public List<ProjectGroup> findByNameLike(String name) {
        String nameLike = "%" + name + "%";
        List<ProjectGroup> results = dao.findByNameLike(nameLike);
        if (!Objects.isNull(results)) {
            return results;
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * 数据保存操作
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OperateResultWithData<ProjectGroup> save(ProjectGroup entity) {
        String name = entity.getName();
        if (StringUtils.isBlank(name)) {
            return OperateResultWithData.operationFailure("项目组名称不能为空.");
        }

        String path = entity.getGroupPath();
        if (StringUtils.isBlank(path)) {
            path = name.toLowerCase();
        }
        entity.setGroupPath(path);
        if (StringUtils.isBlank(entity.getId())) {
            // gitlab的群组id
            String groupId = entity.getCode();
            if (StringUtils.isNotBlank(groupId)) {
                ResultData<Group> resultData = gitlabService.getGroup(groupId);
                if (resultData.failed()) {
                    Group group = new Group();
                    group.setName(name);
                    group.setPath(path);
                    String parentCode = entity.getParentCode();
                    if (StringUtils.isNotBlank(parentCode) && StringUtils.isNumeric(parentCode)) {
                        group.setParentId(Integer.valueOf(parentCode));
                    }
                    group.setDescription(entity.getRemark());
                    ResultData<String> result = gitlabService.createGroup(group);
                    if (result.successful()) {
                        entity.setCode(result.getData());
                    } else {
                        // 事务回滚
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return OperateResultWithData.operationFailure(result.getMessage());
                    }
                }
            } else {
                // 检查path是否在gitlab存在
                ResultData<Group> resultData = gitlabService.getGroup(path);
                if (resultData.successful()) {
                    Group gitGroup = resultData.getData();
                    entity.setCode(String.valueOf(gitGroup.getId()));
                } else {
                    Group group = new Group();
                    group.setName(name);
                    group.setPath(path);
                    String parentCode = entity.getParentCode();
                    if (StringUtils.isNotBlank(parentCode) && StringUtils.isNumeric(parentCode)) {
                        group.setParentId(Integer.valueOf(parentCode));
                    }
                    group.setDescription(entity.getRemark());
                    ResultData<String> result = gitlabService.createGroup(group);
                    if (result.successful()) {
                        entity.setCode(result.getData());
                    } else {
                        // 事务回滚
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return OperateResultWithData.operationFailure(resultData.getMessage());
                    }
                }
            }
        } else {
            ResultData<Void> resultData = gitlabService.updateGroup(path, entity.getRemark());
            if (resultData.failed()) {
                // 事务回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return OperateResultWithData.operationFailure(resultData.getMessage());
            }
        }
        OperateResultWithData<ProjectGroup> result = super.save(entity);
        if (result.successful()) {
            if (StringUtils.isNotBlank(entity.getManagerAccount())) {
                // 指定组管理员
                ContextUtil.getBean(ProjectUserService.class).assign(entity.getManagerAccount(), entity.getId(), entity.getName(), ObjectType.PROJECT);
            }
        }
        return result;
    }

    /**
     * 删除数据保存数据之前额外操作回调方法 子类根据需要覆写添加逻辑即可
     *
     * @param s 待删除数据对象主键
     */
    @Override
    protected OperateResult preDelete(String s) {
        ProjectGroup userGroup = dao.findOne(s);
        if (Objects.isNull(userGroup)) {
            return OperateResult.operationFailure("用户组[" + s + "]不存在！");
        }
        if (applicationService.isExistsByProperty(Application.FIELD_GROUP_CODE, userGroup.getCode())) {
            return OperateResult.operationFailure("[" + userGroup.getCode() + "]应用存在应用模块,不允许删除!");
        }
//        ResultData<Void> resultData = gitlabService.deleteGroup(userGroup.getCode());
//        if (resultData.failed()) {
//            return OperateResult.operationFailure(resultData.getMessage());
//        }
        OperateResult result = super.preDelete(s);
        if (result.successful()) {
            // 移除组管理员授权
            ContextUtil.getBean(ProjectUserService.class).cancelAssign(userGroup.getId(), Sets.newHashSet(userGroup.getManagerAccount()));
        }
        return result;
    }

    /**
     * 同步gitlab群组
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultData<Void> syncGitlabData() {
        List<ProjectGroup> groupList = dao.findAll();
        Map<String, ProjectGroup> groupMap = groupList.stream().collect(Collectors.toMap(ProjectGroup::getCode, g -> g));

        List<ProjectGroup> nodes = getGitlabGroupTree();
        saveTree(null, nodes, groupMap);
        return ResultData.success();
    }

    private void saveTree(String parentId, List<ProjectGroup> nodeList, Map<String, ProjectGroup> groupMap) {
        ProjectGroup entity;
        for (ProjectGroup group : nodeList) {
            System.out.println("保存 " + group.getCode() + "  " + group.getName());
            entity = groupMap.get(group.getCode());
            if (Objects.isNull(entity)) {
                entity = group;
            } else {
                entity.setName(group.getName());
                entity.setGroupPath(group.getGroupPath());
                entity.setRemark(group.getRemark());
            }
            entity.setParentId(parentId);
            this.save(entity);

            List<ProjectGroup> childrenList = group.getChildren();
            if (CollectionUtils.isNotEmpty(childrenList)) {
                saveTree(entity.getId(), childrenList, groupMap);
            }
        }
    }

    /**
     * 获取gitlab群组清单
     */
    public List<ProjectGroup> getGitlabGroupTree() {
        List<ProjectGroup> result = new ArrayList<>();
        ResultData<List<Group>> resultData = gitlabService.getGroups();
        if (resultData.successful()) {
            ProjectGroup groupDto;
            List<Group> groups = resultData.getData();
            List<ProjectGroup> dtos = new ArrayList<>();
            for (Group group : groups) {
                Integer parentCode = group.getParentId();
                groupDto = new ProjectGroup();
                groupDto.setCode(String.valueOf(group.getId()));
                groupDto.setName(group.getName());
                groupDto.setGroupPath(group.getPath());
                groupDto.setParentCode(Objects.isNull(parentCode) ? null : String.valueOf(parentCode));
                groupDto.setRemark(group.getDescription());
                dtos.add(groupDto);
            }

            for (ProjectGroup group : dtos) {
                if (StringUtils.isBlank(group.getParentCode())) {
                    findChildren(group, dtos);
                    result.add(group);
                }
            }
            result = result.stream().sorted(Comparator.comparing(ProjectGroup::getCode)).collect(Collectors.toList());
        }
        return result;
    }

    /**
     * 递归查找子节点并设置子节点
     *
     * @param treeNode 树形节点（顶级节点）
     * @param nodes    节点清单
     * @return 树形节点
     */
    private ProjectGroup findChildren(ProjectGroup treeNode, List<ProjectGroup> nodes) {
        for (ProjectGroup node : nodes) {
            if (StringUtils.equals(treeNode.getCode(), node.getParentCode())) {
                if (Objects.isNull(treeNode.getChildren())) {
                    treeNode.setChildren(new ArrayList<>());
                }
                treeNode.getChildren().add(findChildren(node, nodes));
            }
        }
        return treeNode;
    }
}