package com.shopme.ecom.admin.user;

import com.shopme.ecom.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends PagingAndSortingRepository<User, Integer>, CrudRepository<User, Integer> {

     @Query("Select u from User u where u.email = :email")
     User getUserByEmail(@Param("email") String email);

     Long countById(Integer id);

     @Query("SELECT u from User u where u.firstName LIKE %?1% OR u.lastName LIKE %?1% OR u.email LIKE %?1%")
     public Page<User> findAll(String keyword, Pageable pageable);

     @Query("UPDATE User u set u.enabled= ?2 where u.id = ?1 ")
     @Modifying
     void updateEnabledStatus(Integer id, boolean enabled);
}
