package com.springapp.mvc.vo;

import java.util.List;

public class MenuDto {

    String menuName;

    String url;

    String logoTag;

    //"_blank"
    String target;

    String id;

    String parentId;

    List<MenuDto> sysMenuList;

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLogoTag() {
        return logoTag;
    }

    public void setLogoTag(String logoTag) {
        this.logoTag = logoTag;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public List<MenuDto> getSysMenuList() {
        return sysMenuList;
    }

    public void setSysMenuList(List<MenuDto> sysMenuList) {
        this.sysMenuList = sysMenuList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
