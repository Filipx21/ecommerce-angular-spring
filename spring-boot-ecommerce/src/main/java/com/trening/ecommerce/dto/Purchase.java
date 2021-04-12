package com.trening.ecommerce.dto;

import com.trening.ecommerce.entity.Address;
import com.trening.ecommerce.entity.Customer;
import com.trening.ecommerce.entity.Order;
import com.trening.ecommerce.entity.OrderItem;
import lombok.Data;

import java.util.Set;

@Data
public class Purchase {

    private Customer customer;
    private Address shippingAddress;
    private Address billingAddress;
    private Order order;
    private Set<OrderItem> orderItems;

}
