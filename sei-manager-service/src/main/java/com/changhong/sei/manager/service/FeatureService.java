package com.changhong.sei.manager.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.core.service.bo.OperateResultWithData;
import com.changhong.sei.manager.commom.Constants;
import com.changhong.sei.manager.dao.FeatureDao;
import com.changhong.sei.manager.entity.Feature;
import com.changhong.sei.manager.entity.Menu;
import com.changhong.sei.manager.entity.Role;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 功能项权限业务逻辑实现类
 *
 * @author sei
 * @since 2020-11-10 16:24:33
 */
@Service("featureService")
public class FeatureService extends BaseEntityService<Feature> {
    @Autowired
    private FeatureDao dao;
    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleFeatureService roleFeatureService;

    @Override
    protected BaseEntityDao<Feature> getDao() {
        return dao;
    }

    /**
     * 根据角色列表查询权限列表
     *
     * @param ids 角色id列表
     * @return 权限列表
     */
    public List<Feature> selectByRoleIdList(List<String> ids) {
        return dao.selectByRoleIdList(ids);
    }

    /**
     * 重写save 保存的时除去首尾的'/'
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OperateResultWithData<Feature> save(Feature entity) {
        String parentId = entity.getParentId();
        if (StringUtils.isNotBlank(parentId) && !Constants.NULL_EMPTY.equals(parentId)) {
            // 检查父级功能项
            Feature parentFeature = findOne(parentId);
            if (Objects.isNull(parentFeature)) {
                return OperateResultWithData.operationFailure("父级功能项不存在.");
            }
        } else {
            entity.setParentId(Constants.NULL_EMPTY);
        }
        return super.save(entity);
    }

    /**
     * 删除数据保存数据之前额外操作回调方法 子类根据需要覆写添加逻辑即可
     *
     * @param id 待删除数据对象主键
     */
    @Override
    protected OperateResult preDelete(String id) {
        // 检查是否存在下级功能项，如果存在禁止删除
        Feature feature = dao.findOne(id);
        if (Objects.isNull(feature)) {
            // 需要删除的业务实体不存在！id=【{0}】
            return OperateResult.operationFailure("需要删除的业务实体不存在！id=【" + id + "】");
        }
        // 获取下级功能项
        List<Feature> childFeatures = dao.findListByProperty(Feature.FIELD_PARENT_ID, id);
        if (CollectionUtils.isNotEmpty(childFeatures)) {
            // 页面【{0}】存在下级功能项，禁止删除！
            return OperateResult.operationFailure("页面【" + feature.getName() + "】存在下级功能项，禁止删除！");
        }
        List<Menu> menus = menuService.findByFeatureId(id);
        if (menus != null && menus.size() > 0) {
            //该功能项存在菜单，禁止删除！
            return OperateResult.operationFailure("该功能项存在菜单，禁止删除！");
        }
        List<Role> roles = roleFeatureService.getParentsFromChildId(id);
        if (roles != null && !roles.isEmpty()) {
            //该功能项已分配功能角色，禁止删除！
            return OperateResult.operationFailure("该功能项已分配功能角色，禁止删除！");
        }
        return super.preDelete(id);
    }

    /**
     * 根据功能项id查询子功能项
     *
     * @param featureId 功能项的id
     * @return 功能项列表
     */
    public List<Feature> findChildByFeatureId(String featureId) {
        Feature parentFeature = findOne(featureId);
        if (Objects.isNull(parentFeature)) {
            return new ArrayList<>();
        }
        return dao.findListByProperty(Feature.FIELD_PARENT_ID, featureId);
    }
}