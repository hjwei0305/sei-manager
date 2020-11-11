package com.changhong.sei.manager.api;

import com.changhong.sei.core.api.BaseTreeApi;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.manager.dto.MenuDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 实现功能: 系统菜单API接口
 *
 * @author 王锦光 wangjg
 * @version 2020-01-19 21:56
 */
@FeignClient(name = "sei-manager", path = "menu")
public interface MenuApi extends BaseTreeApi<MenuDto> {
    /**
     * 获取整个菜单树
     *
     * @return 菜单树形对象集合
     */
    @GetMapping(path = "getMenuTree")
    @ApiOperation(notes = "查询所有的系统菜单树", value = "查询所有的系统菜单树")
    ResultData<List<MenuDto>> getMenuTree();

    /**
     * 根据名称模糊查询
     *
     * @param name 名称
     * @return 返回的列表
     */
    @GetMapping(path = "findByNameLike")
    @ApiOperation(value = "根据名称模糊查询", notes = "根据名称模糊查询")
    ResultData<List<MenuDto>> findByNameLike(@RequestParam("name") String name);
}
