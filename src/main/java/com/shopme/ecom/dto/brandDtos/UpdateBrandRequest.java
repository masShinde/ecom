package com.shopme.ecom.dto.brandDtos;

import java.util.HashSet;
import java.util.Set;

public class UpdateBrandRequest implements IBrandRequest {

    private Integer id;

    private String name;

    private String logo;

    private Set<Integer> categories = new HashSet<>();


    public UpdateBrandRequest() {
    }

    public UpdateBrandRequest(Integer id, String name, String logo) {
        this.id = id;
        this.name = name;
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Set<Integer> getCategories() {
        return categories;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCategories(Set<Integer> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "CreateBrandRequest{" +
                ", name='" + name + '\'' +
                ", logo='" + logo + '\'' +
                '}';
    }

}
