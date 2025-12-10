package com.ruoyi.system.service;

import com.ruoyi.system.domain.dto.PlaceOrderRequest;

public interface OrderService
{
    // place order interface
    void placeOrder(PlaceOrderRequest placeOrderRequest);
}