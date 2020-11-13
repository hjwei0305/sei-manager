package com.changhong.sei.manager.dto;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-11-13 09:29
 */
public class UserAuthorizedResponse implements Serializable {
    private static final long serialVersionUID = -5700083766037338064L;

    public List<MenuDto> menus;
    public Map<String, Collection<String>> permissions;

    public UserAuthorizedResponse() {

    }

    public UserAuthorizedResponse(List<MenuDto> menus, Map<String, Collection<String>> permissions) {
        this.menus = menus;
        this.permissions = permissions;
    }

    public List<MenuDto> getMenus() {
        return menus;
    }

    public void setMenus(List<MenuDto> menus) {
        this.menus = menus;
    }

    public Map<String, Collection<String>> getPermissions() {
        return permissions;
    }

    public void setPermissions(Map<String, Collection<String>> permissions) {
        this.permissions = permissions;
    }
}
