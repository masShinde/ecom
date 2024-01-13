package com.shopme.ecom.admin.user;


import com.shopme.ecom.entities.*;
import com.shopme.ecom.enums.OrderStatus;
import com.shopme.ecom.enums.PaymentMethod;
import com.shopme.ecom.services.AddressService;
import jakarta.persistence.EntityManager;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Date;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class OrderRepoTests {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private EntityManager entityManager;


    @Test
    public void TestAddNewOrder(){
        User user = entityManager.find(User.class, 1);
        Product product = entityManager.find(Product.class, 1);
//        AddressInfo addressInfo = addressService.getSavedAddress(1).get(0);
        Order mainOrder = new Order();
//        mainOrder.setAddressInfo(addressInfo);
        mainOrder.setUser(user);
        mainOrder.setShippingCost(10);
        mainOrder.setProductCost(100);
        mainOrder.setTax(0);
        mainOrder.setSubTotal(product.getPrice());
        mainOrder.setTotal(product.getPrice()+10);

        mainOrder.setPaymentMethod(PaymentMethod.CREDIT_CARD);
        mainOrder.setStatus(OrderStatus.NEW);
        mainOrder.setDeliveryDate(new Date());

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProduct(product);
        orderDetail.setProductCost(product.getCost());
        orderDetail.setShippingCost(10);
        orderDetail.setSubTotal(product.getPrice());
        orderDetail.setUnitPrice(product.getPrice());
        Set<OrderDetail> orderDetails = mainOrder.getOrderDetails();
        orderDetails.add(orderDetail);
        mainOrder.setOrderDetails(orderDetails);
        Order savedOrder = orderRepository.save(mainOrder);
        assertThat(savedOrder.getId()).isGreaterThan(0);
    }



}
