package com.trening.ecommerce.service;

import com.trening.ecommerce.dto.Purchase;
import com.trening.ecommerce.dto.PurchaseResponse;

public interface CheckoutService {

    PurchaseResponse placeOrder(Purchase purchase);


}
