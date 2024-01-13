package com.shopme.ecom.dto.categoryDtos;

import com.shopme.ecom.entities.Category;

import java.util.HashSet;
import java.util.Set;

public class CreateCategoryRequest {


    private String name;

    private String alias;

    private String image;

    private boolean enabled;

    private Integer parent;

    private Set<Integer> children = new HashSet<>();


    public CreateCategoryRequest() {
    }

    public CreateCategoryRequest(String name, String alias, String image){
        this.name = name;
        this.alias = alias;
        this.image = image == null ? "default.png" : image;
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
                "name='" + name + '\'' +
                ", alias='" + alias + '\'' +
                ", image='" + image + '\'' +
                ", enabled=" + enabled +
                ", parent=" + parent +
                ", children=" + children +
                '}';
    }
}
