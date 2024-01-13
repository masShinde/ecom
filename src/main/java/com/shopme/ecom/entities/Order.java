package com.shopme.ecom.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shopme.ecom.enums.OrderStatus;
import com.shopme.ecom.enums.PaymentMethod;
import jakarta.persistence.*;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Cascade;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//    @JsonIgnore
//    @ManyToOne
//    @JoinColumn(name = "addressInfo_id")
//    private AddressInfo addressInfo;

    @Column(name = "order_time")
    private Date orderTime;

    @Column(name = "shipping_cost")
    private float shippingCost;

    @Column(name = "product_cost")
    private float productCost;

    @Column(name = "sub_total")
    private float subTotal;

    @Column(name = "tax")
    private float tax;

    @Column(name = "total")
    private float total;

    @Column(name = "delivery_date")
    private Date deliveryDate;

    @Column(name = "payment_method")
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany
    @Column(name = "order_details")
    @Cascade({CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<OrderDetail> orderDetails = new HashSet<>();


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

//    public AddressInfo getAddressInfo() {
//        return addressInfo;
//    }
//
//    public void setAddressInfo(AddressInfo addressInfo) {
//        this.addressInfo = addressInfo;
//    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public float getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(float shippingCost) {
        this.shippingCost = shippingCost;
    }

    public float getProductCost() {
        return productCost;
    }

    public void setProductCost(float productCost) {
        this.productCost = productCost;
    }

    public float getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(float subTotal) {
        this.subTotal = subTotal;
    }

    public float getTax() {
        return tax;
    }

    public void setTax(float tax) {
        this.tax = tax;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(Set<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }
}
