package com.shopme.ecom.dto.categoryDtos;

import com.shopme.ecom.entities.Category;

import java.util.HashSet;
import java.util.Set;

public class UpdateCategoryRequest {

    private Integer id;

    private String name;

    private String alias;

    private String image;

    private boolean enabled;

    private Integer parent;

    private Set<Integer> children = new HashSet<>();


    public UpdateCategoryRequest() {
    }

    public UpdateCategoryRequest(Integer id, String name, String alias, String image){
        this.id = id;
        this.name = name;
        this.alias = alias;
        this.image = image == null ? "default.png" : image;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public Set<Integer> getChildren() {
        return children;
    }

    public void setChildren(Set<Integer> children) {
        this.children = children;
    }


    @Override
    public String toString() {
        return "CreateCategoryRequest{" +
                "id='" + id + '\'' +
                "name='" + name + '\'' +
                ", alias='" + alias + '\'' +
                ", image='" + image + '\'' +
                ", enabled=" + enabled +
                ", parent=" + parent +
                ", children=" + children +
                '}';
    }

}
