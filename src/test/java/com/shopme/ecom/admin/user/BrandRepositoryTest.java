package com.shopme.ecom.admin.user;

import com.shopme.ecom.entities.Brand;
import com.shopme.ecom.entities.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class BrandRepositoryTest {

    @Autowired
    private BrandRepository brandRepository;

    @Test
    public void testCreateNewBrand(){
        Category c = new Category(1);
        Brand acer = new Brand("Acer", "default.png");
        acer.getCategories().add(c);

        Brand savedBrand = brandRepository.save(acer);
        assertThat(savedBrand).isNotNull();
        assertThat(savedBrand.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateNewBrandWithMultipleCategories(){
        Category c = new Category(9);
        Category c2 = new Category(8);
        Brand samsung = new Brand("samsung", "default.png");
        samsung.getCategories().add(c);
        samsung.getCategories().add(c2);
        Brand savedBrand = brandRepository.save(samsung);
        assertThat(savedBrand).isNotNull();
        assertThat(savedBrand.getId()).isGreaterThan(0);
    }


    @Test
    public void testGetAllBrands(){
        Iterable<Brand> brands = brandRepository.findAll();
        List<Brand> brandList = new ArrayList<>();
        for(Brand b: brands){
            brandList.add(b);
        }
        assertThat(brandList.size()).isGreaterThan(0);
        assertThat(brandList.size()).isEqualTo(2);
    }

    @Test
    public void testGetBrandById(){
        Brand brand = brandRepository.findById(1).get();
        assertThat(brand).isNotNull();
        assertThat(brand.getId()).isGreaterThan(0);
    }

    @Test
    public void testUpdateBrand(){
        int id = 3;
        Brand brand = brandRepository.findById(id).get();
        String newName = "Samsung Electronics";
        brand.setName("Samsung Electronics");
        brandRepository.save(brand);

        assertThat(brand.getName()).isEqualTo(newName);
        assertThat(brand.getId()).isEqualTo(id);
    }

    @Test
    public void testDeleteBrand(){
        int id = 3;
        brandRepository.deleteById(id);
        Optional<Brand> brand = brandRepository.findById(id);
        assertThat(brand.isEmpty()).isTrue();
    }

}
