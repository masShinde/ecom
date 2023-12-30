package com.shopme.ecom.admin.user;

import com.shopme.ecom.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer>, CrudRepository<Category, Integer> {

    Iterable<Category> findAll(Sort sort);

    Category findByName(String name);

    Category findByAlias(String alias);

    @Query("SELECT c FROM Category c where c.name LIKE %?1%")
    public Page<Category> search(String keyword, Pageable pageable);

}
