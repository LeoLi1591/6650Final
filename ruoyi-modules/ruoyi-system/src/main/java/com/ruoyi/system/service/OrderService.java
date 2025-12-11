package com.ruoyi.system.service;

import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.system.domain.Order;
import com.ruoyi.system.domain.dto.PlaceOrderRequest;

import java.util.List;

public interface OrderService
{
    // place order interface
    AjaxResult placeOrder(PlaceOrderRequest placeOrderRequest);

    List<Order> selectOrderList(PlaceOrderRequest placeOrderRequest);
}