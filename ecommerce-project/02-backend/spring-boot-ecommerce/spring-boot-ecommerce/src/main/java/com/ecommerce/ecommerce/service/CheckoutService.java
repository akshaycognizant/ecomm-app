package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.dto.Purchase;
import com.ecommerce.ecommerce.dto.PurchaseResponse;

public interface CheckoutService {
    PurchaseResponse placeOrder(Purchase purchase);
}
