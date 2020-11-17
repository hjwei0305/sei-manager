package com.changhong.sei.manager.service;

import com.changhong.sei.core.dao.BaseTreeDao;
import com.changhong.sei.core.dto.serach.SearchFilter;
import com.changhong.sei.core.service.BaseTreeService;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.core.service.bo.OperateResultWithData;
import com.changhong.sei.manager.dao.MenuDao;
import com.changhong.sei.manager.entity.Feature;
import com.changhong.sei.manager.entity.Menu;
import com.changhong.sei.util.IdGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Predicate;


/**
 * 角色业务逻辑实现类
 *
 * @author sei
 * @since 2020-11-10 16:24:33
 */
@Service("menuService")
public class MenuService extends BaseTreeService<Menu> {

    @Autowired
    private MenuDao menuDao;
    @Autowired
    private FeatureService featureService;

    @Override
    protected BaseTreeDao<Menu> getDao() {
        return menuDao;
    }

    /**
     * 基于主键集合查询集合数据对象
     */
    @Override
    public List<Menu> findAll() {
        return super.findAll();
    }

    /**
     * 保存菜单项
     *
     * @param menu 要保存的菜单
     * @return 操作后的结果
     */
    @Override
    public OperateResultWithData<Menu> save(Menu menu) {
        // 检查父节点
        OperateResult checkResult = checkParentNode(menu.getParentId());
        if (checkResult.notSuccessful()) {
            return OperateResult.converterWithData(checkResult, menu);
        }
        // 检查功能项是否已经配置给其他菜单
        OperateResult checkFeatureResult = checkFeature(menu.getId(), menu.getFeatureId());
        if (checkFeatureResult.notSuccessful()) {
            return OperateResult.converterWithData(checkFeatureResult, menu);
        }
        if (StringUtils.isBlank(menu.getCode())) {
            menu.setCode(String.valueOf(IdGenerator.nextId()));
        }
        OperateResultWithData<Menu> result = super.save(menu);
        if (result.successful()) {
            Menu data = result.getData();
            if (Objects.nonNull(data) && StringUtils.isNotBlank(data.getFeatureId())) {
                Feature feature = featureService.findOne(data.getFeatureId());
                data.setFeature(feature);
            }
        }
        return result;
    }

    /**
     * 通过Id标识删除一个树节点
     *
     * @param id 主键
     * @return 操作结果
     */
    @Override
    public OperateResult delete(String id) {
        return super.delete(id);
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
     * 检查功能项是否已经配置给其他菜单
     *
     * @param id        菜单Id
     * @param featureId 功能项Id
     * @return 检查结果
     */
    private OperateResult checkFeature(String id, String featureId) {
        if (StringUtils.isBlank(featureId)) {
            // 检查通过！
            return OperateResult.operationSuccess("检查通过！");
        }
        String checkId = id;
        if (StringUtils.isBlank(checkId)) {
            checkId = "";
        }
        Menu otherMenu = menuDao.findFirstByFeatureIdAndIdNot(featureId, checkId);
        if (Objects.nonNull(otherMenu)) {
            // 菜单【{0}】已经配置了功能项【{1}】，不能再次配置！
            return OperateResult.operationFailure("菜单【" + otherMenu.getName() + "】已经配置了功能项【" + otherMenu.getFeature().getName() + "】，不能再次配置！");
        }
        // 检查通过！
        return OperateResult.operationSuccess("检查通过！");
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
        Menu parent = menuDao.findOne(parentId);
        if (Objects.isNull(parent)) {
            // 检查通过！
            return OperateResult.operationSuccess("检查通过！");
        }
        if (StringUtils.isNotBlank(parent.getFeatureId())) {
            // 菜单【{0}】已经配置了功能项，禁止作为父级菜单！
            return OperateResult.operationFailure("菜单【"+parent.getName()+"】已经配置了功能项，禁止作为父级菜单！");
        }
        // 检查通过！
        return OperateResult.operationSuccess("检查通过！");
    }

    /**
     * 获取整个Menu多根树的树形对象
     *
     * @return Menu多根树对象集合
     */
    public List<Menu> getMenuTree() {
        List<Menu> rootTree = getAllRootNode();
        List<Menu> rootMenuTree = new ArrayList<>();
        for (Menu aRootTree : rootTree) {
            Menu menu = getTree(aRootTree.getId());
            rootMenuTree.add(menu);
        }
        for (Menu menu : rootMenuTree) {
            translateBaseAddress(menu);
        }
        return rootMenuTree;
    }

    /**
     * 翻译基地址
     */
    private void translateBaseAddress(Menu menu) {
        if (menu == null) {
            return;
        }
//        if (menu.getFeature() != null) {
//            if (menu.getFeature().getFeatureGroup() != null) {
//                if (menu.getFeature().getFeatureGroup().getAppModule() != null) {
//                    AppModule appModule = menu.getFeature().getFeatureGroup().getAppModule();
//                    String apiBaseAddress = appModule.getApiBaseAddress();
//                    if (StringUtils.isNotBlank(apiBaseAddress)) {
//                        String baseAddress = ContextUtil.getProperty(apiBaseAddress);
//                        menu.getFeature().getFeatureGroup().getAppModule().setApiBaseAddress(baseAddress);
//                    }
//                    String webBaseAddress = appModule.getWebBaseAddress();
//                    if (StringUtils.isNotBlank(webBaseAddress)) {
//                        String baseAddress = ContextUtil.getProperty(webBaseAddress);
//                        menu.getFeature().getFeatureGroup().getAppModule().setWebBaseAddress(baseAddress);
//                    }
//                }
//            }
//        }
//        List<Menu> children = menu.getChildren();
//        if (children != null && children.size() > 0) {
//            for (Menu tempMenu : children) {
//                translateBaseAddress(tempMenu);
//            }
//        }
    }

    /**
     * 通过功能项查询
     *
     * @param featureId 查询关键字
     * @return 查询结果集
     */
    public List<Menu> findByFeatureId(String featureId) {
        return menuDao.findByFeatureId(featureId);
    }

    /**
     * 获取名称匹配的菜单
     *
     * @param name 名称
     */
    public List<Menu> findByNameLike(String name) {
        String nameLike = "%" + name + "%";
        List<Menu> results = menuDao.findByNameLike(nameLike);
        if (!Objects.isNull(results)) {
            return results;
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * 通过功能项清单获取菜单
     *
     * @param features 功能项清单
     * @return 菜单
     */
    List<Menu> findByFeatures(List<Feature> features) {
        if (features == null || features.size() == 0) {
            return Collections.emptyList();
        }
        List<String> menuFeatureIds = new ArrayList<>();
        features.forEach((f) -> {
            // 页面功能
            if (f.getType() == 1) {
                menuFeatureIds.add(f.getId());
            }
        });
        //修复列表为空时，查询全部的数据
        if (menuFeatureIds.size() == 0) {
            return Collections.emptyList();
        }
        //通过功能项获取菜单项
        SearchFilter filter = new SearchFilter("feature.id", menuFeatureIds, SearchFilter.Operator.IN);
        return findByFilter(filter);
    }

    /**
     * 通过功能项清单获取菜单
     *
     * @param features 功能项清单
     * @param allMenus 所有菜单项
     * @return 菜单
     */
    public static List<Menu> getMenusByFeatures(List<Feature> features, List<Menu> allMenus) {
        if (CollectionUtils.isEmpty(features) || CollectionUtils.isEmpty(allMenus)) {
            return Collections.emptyList();
        }
        Set<Menu> featureMenus = new LinkedHashSet<>();
        features.forEach((f) -> {
            // 页面功能
            if (Objects.nonNull(f.getType()) && f.getType() == 1) {
                Optional<Menu> menuOptional = allMenus.stream().filter(m -> StringUtils.equals(f.getId(), m.getFeatureId())).findAny();
                menuOptional.ifPresent(featureMenus::add);
            }
        });
        return new LinkedList<>(featureMenus);
    }

    /**
     * 生成所有菜单节点
     *
     * @param userMenus 用户的功能项菜单节点
     * @param nodes     功能项菜单节点
     * @param allMenus  所有菜单项
     */
    public static void generateUserMenuNodes(Set<Menu> userMenus, List<Menu> nodes, List<Menu> allMenus) {
        nodes.forEach(node -> {
            // 获取父节点
            getParentMenu(userMenus, node, allMenus);
        });
    }

    private static void getParentMenu(Set<Menu> userMenus, Menu node, List<Menu> allMenus) {
        // 获取父节点
        String parentId = node.getParentId();
        if (StringUtils.isNotBlank(parentId)) {
            // 判断是否已经包含在结果中
            Predicate<Menu> predicate = menu -> StringUtils.equals(parentId, menu.getId());
            if (userMenus.stream().anyMatch(predicate)) {
                return;
            }
            Optional<Menu> menuOptional = allMenus.stream().filter(m -> StringUtils.equals(parentId, m.getId())).findAny();
            if (menuOptional.isPresent()) {
                Menu parentNode = menuOptional.get();
                userMenus.add(parentNode);
                getParentMenu(userMenus, parentNode, allMenus);
            }
        }
    }
}