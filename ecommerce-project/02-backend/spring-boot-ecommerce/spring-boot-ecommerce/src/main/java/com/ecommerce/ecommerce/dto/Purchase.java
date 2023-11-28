package com.ecommerce.ecommerce.dto;

import com.ecommerce.ecommerce.entity.Address;
import com.ecommerce.ecommerce.entity.Customer;
import com.ecommerce.ecommerce.entity.Order;
import com.ecommerce.ecommerce.entity.OrderItem;
import lombok.Data;

import java.util.Set;

@Data //lombok annotation
public class Purchase {
    private Customer customer;
    private Address shippingAddress;
    private Address billingAddress;
    private Order order;
    private Set<OrderItem> orderItems;
}
