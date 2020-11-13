package com.changhong.sei.manager.service;

import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.dao.BaseRelationDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseRelationService;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.manager.dao.RoleFeatureDao;
import com.changhong.sei.manager.dto.AuthTreeDto;
import com.changhong.sei.manager.dto.FeatureDto;
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
}
