package com.shopme.ecom.admin.user;

import com.shopme.ecom.entities.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrderRepository extends PagingAndSortingRepository<Order, Integer>, CrudRepository<Order, Integer> {
}
