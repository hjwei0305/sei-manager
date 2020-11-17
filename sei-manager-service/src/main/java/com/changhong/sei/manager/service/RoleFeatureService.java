package com.changhong.sei.manager.service;

import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.dao.BaseRelationDao;
import com.changhong.sei.core.service.BaseRelationService;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.exception.ServiceException;
import com.changhong.sei.manager.dao.RoleFeatureDao;
import com.changhong.sei.manager.dto.FeatureNode;
import com.changhong.sei.manager.entity.Feature;
import com.changhong.sei.manager.entity.Role;
import com.changhong.sei.manager.entity.RoleFeature;
import com.changhong.sei.manager.entity.UserRole;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 实现功能：功能角色分配的功能项的业务逻辑实现
 */
@Service
public class RoleFeatureService extends BaseRelationService<RoleFeature, Role, Feature> {
    @Autowired
    private RoleFeatureDao dao;
    @Autowired
    private FeatureService featureService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userFeatureRoleService;

    @Override
    protected BaseRelationDao<RoleFeature, Role, Feature> getDao() {
        return dao;
    }

    /**
     * 获取可以分配的子实体清单
     *
     * @return 子实体清单
     */
    @Override
    protected List<Feature> getCanAssignedChildren(String parentId) {
        String userId = ContextUtil.getUserId();
        return userService.getUserCanAssignFeatures(userId);
    }

    /**
     * 获取未分配的功能项树
     *
     * @param roleId 角色id
     * @return 功能项树清单
     */
    public List<FeatureNode> getUnassignedFeatureTree(String roleId) {
        List<FeatureNode> pageNodes = new LinkedList<>();
        // 获取所有未分配的功能项
        List<Feature> unassignedFeatures = getUnassignedChildren(roleId);
        // 获取所有页面
        List<Feature> pageFeatures = unassignedFeatures.stream().filter(feature -> feature.getType() == 1).collect(Collectors.toList());
        // 检查并生成页面功能项清单
        buildPageFeatures(pageFeatures, unassignedFeatures);
        // 定义所有页面节点
        pageFeatures.forEach(feature -> buildFeatureTree(pageNodes, unassignedFeatures, feature));
        return pageNodes;
    }

    /**
     * 获取角色的功能项树
     *
     * @param roleId 角色id
     * @return 功能项树清单
     */
    public List<FeatureNode> getFeatureTree(String roleId) {
        // 获取所有已分配的功能项
        List<Feature> features = getChildrenFromParentId(roleId);

        // 获取所有页面
        List<Feature> pageFeatures = features.stream().filter(feature -> feature.getType() == 1).collect(Collectors.toList());
        // 定义所有页面节点
        List<FeatureNode> pageNodes = new ArrayList<>();
        pageFeatures.forEach(feature -> {
            // 构造页面的功能项树
            buildFeatureTree(pageNodes, features, feature);
        });
        return pageNodes;
    }

    /**
     * 创建分配关系
     *
     * @param parentId 父实体Id
     * @param childIds 子实体Id清单
     * @return 操作结果
     */
    @Override
    @Transactional
    public OperateResult insertRelations(String parentId, List<String> childIds) {
        UserRole userFeatureRole = userFeatureRoleService.getRelation(ContextUtil.getUserId(), parentId);
        if (userFeatureRole != null) {
            //00037 = 不能给当前用户的功能角色分配功能项！
            return OperateResult.operationFailure("00037");
        }
        OperateResult result = super.insertRelations(parentId, childIds);
        // 清除用户权限缓存
//        AuthorityUtil.cleanAuthorizedCachesByFeatureRoleId(parentId);
        return result;
    }

    /**
     * 移除分配关系
     *
     * @param parentId 父实体Id
     * @param childIds 子实体Id清单
     * @return 操作结果
     */
    @Override
    @Transactional
    public OperateResult removeRelations(String parentId, List<String> childIds) {
        UserRole userFeatureRole = userFeatureRoleService.getRelation(ContextUtil.getUserId(), parentId);
        if (userFeatureRole != null) {
            //00038 = 不能给当前用户的功能角色移除功能项！
            return OperateResult.operationFailure("00038");
        }
        // 清理移除的功能项，如果存在页面项，则移除所有子项
        Set<String> childIdSet = new HashSet<>(childIds);
        childIds.forEach(id -> {
            Feature feature = featureService.findOne(id);
            if (Objects.nonNull(feature) && Objects.nonNull(feature.getType()) && feature.getType() == 1) {
                List<Feature> childFeatures = featureService.findChildByFeatureId(feature.getId());
                if (CollectionUtils.isNotEmpty(childFeatures)) {
                    List<String> featureIds = childFeatures.stream().map(Feature::getId).collect(Collectors.toList());
                    childIdSet.addAll(featureIds);
                }
            }
        });
        OperateResult result = super.removeRelations(parentId, new ArrayList<>(childIdSet));
        if (result.notSuccessful()) {
            return result;
        }
        // 清除用户权限缓存
        //AuthorityUtil.cleanAuthorizedCachesByFeatureRoleId(parentId);
        return result;
    }

    /**
     * 检查并生成页面功能项清单
     *
     * @param pageFeatures 页面功能项
     * @param features     需要展示的功能项
     */
    private void buildPageFeatures(List<Feature> pageFeatures, List<Feature> features) {
        features.forEach(feature -> {
            // 操作功能项检查上级页面功能项是否存在
            if (feature.getType() != 1) {
                Optional<Feature> featureOptional = pageFeatures.stream().filter(f -> Objects.equals(f.getId(), feature.getParentId())).findAny();
                if (!featureOptional.isPresent()) {
                    // 获取菜单项，并追加到页面清单中
                    Feature pageFeature = featureService.findOne(feature.getParentId());
                    if (Objects.isNull(pageFeature)) {
                        throw new ServiceException("功能项【" + feature.getName() + "】配置错误,没有对应的页面功能项.");
                    }
                    pageFeatures.add(pageFeature);
                }
            }
        });
    }

    /**
     * 构造页面的功能项树
     *
     * @param pageNodes 要构造的页面节点清单
     * @param features  使用的功能项清单
     */
    private void buildFeatureTree(List<FeatureNode> pageNodes, List<Feature> features, Feature pageFeature) {
        FeatureNode pageNode = constructNode(pageFeature);
        pageNodes.add(pageNode);
        // 获取所有非页面的功能项
        List<Feature> otherFeatures = features.stream()
                .filter(f -> StringUtils.equals(f.getParentId(), pageFeature.getId())).collect(Collectors.toList());
        otherFeatures.forEach(f -> {
            FeatureNode node = constructNode(f);
            pageNode.getChildren().add(node);
        });
    }

    /**
     * 构造树节点
     *
     * @param feature 功能项
     * @return 功能项节点
     */
    private FeatureNode constructNode(Feature feature) {
        FeatureNode node = new FeatureNode();
        node.setId(feature.getId());
        node.setName(feature.getName());
        node.setType(feature.getType());
        node.setChildren(new LinkedList<>());
        return node;
    }
}
