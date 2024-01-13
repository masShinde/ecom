package com.shopme.ecom.admin.user;


import com.shopme.ecom.entities.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testCreateNewProduct(){
        Product product = new Product("pr1", "pr2", "sd1", "fd1");
        Product savedProduct = productRepository.save(product);

        assertThat(savedProduct.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateMultipleProduct(){
        Product p2 = new Product("p2", "p2", "s2", "f2");
        Product p3 = new Product("p3", "p3", "s3", "f3");
        List<Product> products = (List<Product>) productRepository.saveAll(List.of(p2, p3));
        assertThat(products.size()).isEqualTo(2);
        assertThat(products.get(0).getId()).isGreaterThan(0);
        assertThat(products.get(1).getId()).isGreaterThan(0);
    }

    @Test
    public void testGetProductById(){
        Product product = productRepository.findById(1).get();
        assertThat(product).isNotNull();
        assertThat(product.getId()).isEqualTo(1);
    }

    @Test
    public void testUpdateProduct(){
        Product product = productRepository.findById(1).get();
        product.setAlias("p1");
        product.setFullDescription("f1");
        product.setShortDescription("s1");
        product.setName("p1");
        Product product1 = productRepository.save(product);
        assertThat(product1.getName()).isEqualTo("p1");
    }


}
