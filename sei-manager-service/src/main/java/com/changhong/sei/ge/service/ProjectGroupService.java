package com.changhong.sei.ge.service;

import com.changhong.sei.core.dao.BaseTreeDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseTreeService;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.ge.dao.ProjectGroupDao;
import com.changhong.sei.ge.dto.ProjectGroupDto;
import com.changhong.sei.ge.entity.ProjectGroup;
import com.changhong.sei.integrated.service.GitlabService;
import org.apache.commons.lang3.StringUtils;
import org.gitlab4j.api.models.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public OperateResult move(String nodeId, String targetParentId) {
        // 检查父节点
        OperateResult checkResult = checkParentNode(targetParentId);
        if (checkResult.notSuccessful()) {
            return checkResult;
        }
        return super.move(nodeId, targetParentId);
    }

    /**
     * 检查菜单父节点
     *
     * @param parentId 父节点Id
     * @return 检查结果
     */
    private OperateResult checkParentNode(String parentId) {
        if (StringUtils.isBlank(parentId)) {
            // 检查通过！
            return OperateResult.operationSuccess("检查通过！");
        }
        ProjectGroup parent = dao.findOne(parentId);
        if (Objects.isNull(parent)) {
            // 检查通过！
            return OperateResult.operationSuccess("检查通过！");
        }
        // 检查通过！
        return OperateResult.operationSuccess("检查通过！");
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
     * 获取gitlab群组清单
     */
    public List<ProjectGroupDto> getGitlabGroupTree() {
        List<ProjectGroupDto> result = new ArrayList<>();
        ResultData<List<Group>> resultData = gitlabService.getGroups();
        if (resultData.successful()) {
            ProjectGroupDto groupDto;
            List<Group> groups = resultData.getData();
            List<ProjectGroupDto> dtos = new ArrayList<>();
            for (Group group : groups) {
                Integer parentCode = group.getParentId();
                groupDto = new ProjectGroupDto();
                groupDto.setCode(String.valueOf(group.getId()));
                groupDto.setName(group.getName());
                groupDto.setParentCode(Objects.isNull(parentCode) ? null : String.valueOf(parentCode));
                groupDto.setRemark(group.getDescription());
                dtos.add(groupDto);
            }

            for (ProjectGroupDto group : dtos) {
                if (StringUtils.isBlank(group.getParentCode())) {
                    findChildren(group, dtos);
                    result.add(group);
                }
            }
            result.stream().sorted(Comparator.comparing(ProjectGroupDto::getCode)).collect(Collectors.toList());
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
    private ProjectGroupDto findChildren(ProjectGroupDto treeNode, List<ProjectGroupDto> nodes) {
        for (ProjectGroupDto node : nodes) {
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