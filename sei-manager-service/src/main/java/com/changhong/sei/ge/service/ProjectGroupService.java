package com.changhong.sei.ge.service;

import com.changhong.sei.core.dao.BaseTreeDao;
import com.changhong.sei.core.service.BaseTreeService;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.ge.dao.ProjectGroupDao;
import com.changhong.sei.ge.entity.ProjectGroup;
import com.changhong.sei.manager.entity.Menu;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


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

}