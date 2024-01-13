package com.shopme.ecom.admin.user;

import com.shopme.ecom.entities.AddressInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface AddressRepository extends PagingAndSortingRepository<AddressInfo, Integer>, CrudRepository<AddressInfo, Integer> {

    @Query("select a from AddressInfo a where a.user.id = ?1")
    List<AddressInfo> findByUser(int id);

}
