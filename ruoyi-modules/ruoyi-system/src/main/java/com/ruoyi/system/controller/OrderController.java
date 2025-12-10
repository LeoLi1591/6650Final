package com.ruoyi.system.controller;

import com.ruoyi.common.core.web.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.system.domain.dto.PlaceOrderRequest;
import com.ruoyi.system.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/order")
public class OrderController
{
    @Autowired
    private OrderService orderService;

    @PostMapping("/placeOrder")
    public AjaxResult placeOrder(@Validated @RequestBody PlaceOrderRequest request)
    {

        return orderService.placeOrder(request);
    }
}