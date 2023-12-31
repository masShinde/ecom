package com.shopme.ecom.admin.user;

import com.shopme.ecom.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ProductRepository extends PagingAndSortingRepository<Product, Integer>, CrudRepository<Product, Integer> {

    @Query("SELECT p from Product p where p.name LIKE %?1% or p.alias LIKE %?1% or p.category.name LIKE %?1% or p.brand.name LIKE %?1%")
    Page<Product> searchProduct(String keyword, Pageable pageable);

    @Query("SELECT p from Product p where (p.name = ?1 or p.alias = ?2) and p.id <> ?3")
    List<Product> searchUnique(String name, String alias, Integer id);

    @Query("SELECT p from Product p where p.name = ?1 or p.alias = ?2")
    List<Product> searchUniqueProduct(String name, String alias);


    @Query("SELECT p from Product p where p.category.id = ?1 ")
    Page<Product> getProductByCategory(Integer categoryId, Pageable pageable);
}
