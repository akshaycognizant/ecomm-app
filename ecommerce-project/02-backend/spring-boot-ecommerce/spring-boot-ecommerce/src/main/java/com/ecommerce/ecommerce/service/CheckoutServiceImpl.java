package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.dao.CustomerRepository;
import com.ecommerce.ecommerce.dto.Purchase;
import com.ecommerce.ecommerce.dto.PurchaseResponse;
import com.ecommerce.ecommerce.entity.Customer;
import com.ecommerce.ecommerce.entity.Order;
import com.ecommerce.ecommerce.entity.OrderItem;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class CheckoutServiceImpl implements CheckoutService{
    private CustomerRepository customerRepository;
    @Autowired
    public CheckoutServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {
        // retrieve the order info from dto
        Order order = purchase.getOrder();
        //generate tracking number
        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);
        //populate order with orderItem
        Set<OrderItem> orderItems = purchase.getOrderItems();
        for(OrderItem orderItem : orderItems){
            order.add(orderItem);
        }
        //populate order with billingAddress and shippingAddress
        order.setBillingAddress(purchase.getShippingAddress());
        order.setBillingAddress(purchase.getBillingAddress());
        //populate customer with order
        Customer customer = purchase.getCustomer();
        //check if this is an existing customer
        String theEmail = customer.getEmail();
        Customer customerInDB = customerRepository.findByEmail(theEmail);
        //if yes use existing one
        if(customerInDB != null) {
            customer = customerInDB;
        }
        customer.add(order);
        //save to the database
        customerRepository.save(customer);
        //return a response
        return new PurchaseResponse(orderTrackingNumber);
    }

    private String generateOrderTrackingNumber() {
        //generate a random UUID number(universally unique identifier)
        return UUID.randomUUID().toString();
    }


}
