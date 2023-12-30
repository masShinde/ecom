package com.shopme.ecom.admin.user.categoryTests;

import com.shopme.ecom.admin.user.CategoryRepository;
import com.shopme.ecom.entities.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testCreateCategory(){
        Category newCategory = new Category("Electronics");
        Category savedCategory = categoryRepository.save(newCategory);
        assertThat(savedCategory.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateSubCategory(){
        Category parentCategory = new Category(6);
        Category subCategory = new Category("Memory", parentCategory);
        Category subCategory2 = new Category("Smartphones", parentCategory);
        List<Category> savedCategory = (List<Category>) categoryRepository.saveAll(List.of(subCategory, subCategory2));
        assertThat(savedCategory.size()).isEqualTo(2);
    }

    @Test
    public void testGetCategory(){
        Category c = categoryRepository.findById(1).get();
        Set<Category> children = c.getChildren();

        for(Category subCategory : children){
            System.out.println(subCategory.getName());
        }

        assertThat(children.size()).isGreaterThan(0);
    }

    @Test
    public void testPrintHierarchicalCategories(){
        Iterable<Category> categories = categoryRepository.findAll();

        for(Category category: categories){
            if(category.getParent() == null){
                System.out.println(category.getName());
                Set<Category> children = category.getChildren();

                for(Category subCategory : children){
                    System.out.println("-- "+subCategory.getName());
                    printChildren(subCategory, 1);
                }
            }
        }
    }

    @Test
    public void testFindCategoryByName(){
        String name = "Computers";

        Category category = categoryRepository.findByName(name);

        assertThat(category).isNotNull();
        assertThat(category.getName()).isEqualTo(name);
    }

    @Test
    public void testFindCategoryByAlias(){
        String alias = "Computers";

        Category category = categoryRepository.findByName(alias);

        assertThat(category).isNotNull();
        assertThat(category.getAlias()).isEqualTo(alias);
    }



    private void printChildren(Category parent, int subLevel){
        int newSubLevel = subLevel + 1;

        for(Category subCategory : parent.getChildren()){
            for(int i = 0; i< newSubLevel; i++){
                System.out.print("--");
            }
            System.out.println(subCategory.getName());
        }
    }

}
