package com.shopme.ecom.dto.brandDtos;

import java.util.HashSet;
import java.util.Set;

public class CreateBrandRequest implements IBrandRequest {

    private String name;

    private String logo;

    private Set<Integer> categories = new HashSet<>();


    public CreateBrandRequest() {
    }

    public CreateBrandRequest(String name, String logo) {
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
