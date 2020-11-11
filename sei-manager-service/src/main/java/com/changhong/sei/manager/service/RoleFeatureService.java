package com.changhong.sei.manager.service;

import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.dao.BaseRelationDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseRelationService;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.manager.dao.RoleFeatureDao;
import com.changhong.sei.manager.dto.AuthTreeDto;
import com.changhong.sei.manager.dto.FeatureDto;
import com.changhong.sei.manager.dto.FeatureNode;
import com.changhong.sei.manager.entity.Feature;
import com.changhong.sei.manager.entity.Role;
import com.changhong.sei.manager.entity.RoleFeature;
import com.changhong.sei.manager.entity.UserRole;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    @Autowired
    private ModelMapper modelMapper;

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
     * @param featureRoleId 角色id
     * @return 功能项树清单
     */
    public List<FeatureNode> getUnassignedFeatureTree(String featureRoleId) {
        List<FeatureNode> pageNodes = new LinkedList<>();
        // 获取所有未分配的功能项
        List<Feature> unassignedFeatures = getUnassignedChildren(featureRoleId);
        // 获取所有页面
        List<Feature> menuFeatures = unassignedFeatures.stream().filter(feature -> (Objects.nonNull(feature.getType()) && feature.getType() == 1)).collect(Collectors.toList());
        // 检查并生成页面功能项清单
        buildPageFeatures(menuFeatures, unassignedFeatures);
        // 定义所有页面节点
        menuFeatures.forEach(feature -> buildFeatureTree(pageNodes, unassignedFeatures, feature));
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
     * 根据模块，获取指定个角色授权树
     *
     * @param featureRoleId 角色id
     * @return 指定模块授权树形对象集合
     */
    public ResultData<List<AuthTreeDto>> getAuthTree(String featureRoleId) {
        List<AuthTreeDto> authTreeVos = new ArrayList<>();
        //校验参数
        if (StringUtils.isBlank(featureRoleId)) {
            return ResultData.fail(ContextUtil.getMessage("00067", "featureRoleId"));
        }
        //获取用户全部可分配的功能项 有缓存不用担心数据量
        List<Feature> canAssginedFeatures = this.getCanAssignedChildren(featureRoleId);
        //检查是否属于该功能项组
        if (canAssginedFeatures.size() > 0) {
            //获取已经分配的
            List<Feature> assginedFeatures = this.getChildrenFromParentId(featureRoleId);
            Map<String, String> assginedFeatureIds = assginedFeatures.stream().collect(Collectors.toMap(Feature::getId, Feature::getName));
            //生成AuthTreeVo
            authTreeVos = buildTree(canAssginedFeatures, assginedFeatureIds);
        }
        return ResultData.success(authTreeVos);
    }

    /**
     * 构建权限树
     *
     * @param features         指定模块下所有功能项
     * @param assginedFeatures 指定模块下已分配功能项
     * @return
     */
    private List<AuthTreeDto> buildTree(List<Feature> features, Map<String, String> assginedFeatures) {
        //作为缓存,找寻父节点使用
        Map<String, AuthTreeDto> authTreeVos = new HashMap<>();
        //先对数据排序，页面级在最前面
        List<Feature> sortFeatures = features.stream().sorted(Comparator.comparing(Feature::getType)).collect(Collectors.toList());
        //1.先获取功能项为页面的，作为根节点
        for (Feature feature : sortFeatures) {
            FeatureDto featureDto = modelMapper.map(feature, FeatureDto.class);
            if (Objects.nonNull(feature.getType()) && 1 == feature.getType()) {
                AuthTreeDto authTreeVo = new AuthTreeDto(featureDto);
                //层级
                authTreeVo.setNodeLevel(1);
                //是否勾选
                authTreeVo.setAssigned(Objects.nonNull(assginedFeatures.get(feature.getId())));
                //groupId+url作为key
                authTreeVos.put(feature.getId() + feature.getUrl(), authTreeVo);
            } else {
                //寻找父节点
                AuthTreeDto parent = authTreeVos.get(feature.getId() + feature.getUrl());
                if (Objects.isNull(parent)) {
                    //如果没有父节点，说明数据有问题，先记录 加在根节点
                    // log.warn("功能型[{0}]没有所属页面级功能项,请检查数据", feature.getId());
                    AuthTreeDto authTreeVo = new AuthTreeDto(featureDto);
                    //层级
                    authTreeVo.setNodeLevel(1);
                    //是否勾选
                    authTreeVo.setAssigned(Objects.nonNull(assginedFeatures.get(feature.getId())));
                } else {
                    AuthTreeDto authTreeVo = new AuthTreeDto(featureDto);
                    //层级
                    authTreeVo.setNodeLevel(2);
                    //是否勾选
                    authTreeVo.setAssigned(Objects.nonNull(assginedFeatures.get(feature.getId())));
                    List<AuthTreeDto> children = parent.getChildren();
                    if (Objects.isNull(children)) {
                        children = new ArrayList<>();
                        parent.setChildren(children);
                    }
                    children.add(authTreeVo);
                }
            }
        }
        return new ArrayList<>(authTreeVos.values());
    }

    /**
     * 获取角色的功能项树
     *
     * @param featureRoleId 角色id
     * @return 功能项树清单
     */
    public List<FeatureNode> getFeatureTree(String featureRoleId) {
        List<FeatureNode> appNodes = new LinkedList<>();
        // 获取所有已分配的功能项
        List<Feature> features = getChildrenFromParentId(featureRoleId);
        // 获取所有页面
        List<Feature> menuFeatures = features.stream().filter(feature -> (Objects.nonNull(feature.getType()) && feature.getType() == 1)).collect(Collectors.toList());
        // 定义所有页面节点
        menuFeatures.forEach(feature -> {
            List<FeatureNode> pageNodes = new ArrayList<>();
            // 构造页面的功能项树
            buildFeatureTree(pageNodes, features, feature);
        });
        return appNodes;
    }

    /**
     * 检查并生成页面功能项清单
     *
     * @param menuFeatures 菜单功能项
     * @param features     需要展示的功能项
     */
    private void buildPageFeatures(List<Feature> menuFeatures, List<Feature> features) {
        features.forEach(feature -> {
            // 获取菜单项，并追加到页面清单中
            if (Objects.nonNull(feature.getType()) && feature.getType() == 1) {
                menuFeatures.add(feature);
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
        List<Feature> otherFeatures = features.stream().filter(f -> !StringUtils.equals(f.getId(), pageFeature.getId()))
                .collect(Collectors.toList());
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
