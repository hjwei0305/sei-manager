package com.changhong.sei.manager.controller;

import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.controller.BaseTreeController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.TreeNodeMoveParam;
import com.changhong.sei.core.service.BaseTreeService;
import com.changhong.sei.manager.api.MenuApi;
import com.changhong.sei.manager.dto.MenuDto;
import com.changhong.sei.manager.entity.Menu;
import com.changhong.sei.manager.service.MenuService;
import com.changhong.sei.manager.service.UserService;
import io.swagger.annotations.Api;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 实现功能: 系统菜单API服务
 *
 * @author 王锦光 wangjg
 * @version 2020-01-19 22:09
 */
@RestController
@Api(value = "MenuApi", tags = "系统菜单API服务")
@RequestMapping(path = "menu", produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuController extends BaseTreeController<Menu, MenuDto>
        implements MenuApi {
    @Autowired
    private MenuService menuService;
    @Autowired
    private UserService userService;

    @Override
    public BaseTreeService<Menu> getService() {
        return menuService;
    }

    /**
     * 获取整个菜单树
     *
     * @return 菜单树形对象集合
     */
    @Override
    public ResultData<List<MenuDto>> getMenuTree() {
        List<Menu> menus = menuService.getMenuTree();
        List<MenuDto> dtos = menus.stream().map(this::convertToDto).collect(Collectors.toList());
        return ResultData.success(dtos);
    }

    /**
     * 根据名称模糊查询
     *
     * @param name 名称
     * @return 返回的列表
     */
    @Override
    public ResultData<List<MenuDto>> findByNameLike(String name) {
        List<Menu> menus = menuService.findByNameLike(name);
        List<MenuDto> dtos = menus.stream().map(this::convertToDto).collect(Collectors.toList());
        return ResultData.success(dtos);
    }

    /**
     * 自定义设置Entity转换为DTO的转换器
     */
    @Override
    protected void customConvertToDtoMapper() {
        // 创建自定义映射规则
        PropertyMap<Menu, MenuDto> propertyMap = new PropertyMap<Menu, MenuDto>() {
            @Override
            protected void configure() {
                // 使用自定义转换规则确定FeatureId
                map().setFeatureId(source.getFeatureId());
                map().setFeatureName(source.getFeature().getName());
                map().setMenuUrl(source.getFeature().getUrl());
            }
        };
        // 添加映射器
        dtoModelMapper.addMappings(propertyMap);
    }

    /**
     * 保存业务实体
     *
     * @param dto 业务实体DTO
     * @return 操作结果
     */
    @Override
    public ResultData<MenuDto> save(@Valid MenuDto dto) {
        ResultData<MenuDto> result = super.save(dto);
        if (result.failed()) {
            return result;
        }
        // 清除当前用户的权限缓存
        String userId = ContextUtil.getUserId();
        userService.clearUserAuthorizedCaches(userId);
        return result;
    }

    /**
     * 删除业务实体
     *
     * @param id 业务实体Id
     * @return 操作结果
     */
    @Override
    public ResultData<?> delete(String id) {
        ResultData<?> result = super.delete(id);
        if (result.failed()) {
            return result;
        }
        // 清除当前用户的权限缓存
        String userId = ContextUtil.getUserId();
        userService.clearUserAuthorizedCaches(userId);
        return result;
    }

    /**
     * 移动一个节点
     *
     * @param moveParam 节点移动参数
     * @return 操作状态
     */
    @Override
    public ResultData<?> move(TreeNodeMoveParam moveParam) {
        ResultData<?> result = super.move(moveParam);
        if (result.failed()) {
            return result;
        }
        // 清除当前用户的权限缓存
        String userId = ContextUtil.getUserId();
        userService.clearUserAuthorizedCaches(userId);
        return result;
    }
}
