package com.shopme.ecom.admin.user.categoryTests;

import com.shopme.ecom.admin.user.CategoryRepository;
import com.shopme.ecom.entities.Category;
import com.shopme.ecom.services.CategoryService;
import com.shopme.ecom.enums.DuplicateTypes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class CategoryServiceTests {


    @MockBean
    private CategoryRepository repo;

    @InjectMocks
    private CategoryService service;


    @Test
    public void testCheckUniqueCategory(){
        Integer id = null;
        String name = "Computers";
        String alias = "Computers";

        Category c = new Category(name);
        c.setId(id);
        Mockito.when(repo.findByName(name)).thenReturn(null);
        Mockito.when(repo.findByAlias(name)).thenReturn(c);

        DuplicateTypes result = service.checkIfUnique(id, "", alias);
        assertThat(result).isEqualTo(DuplicateTypes.duplicateAlias);

    }


}
