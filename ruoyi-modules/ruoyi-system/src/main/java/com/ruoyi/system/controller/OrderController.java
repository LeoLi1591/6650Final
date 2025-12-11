package com.ruoyi.system.controller;

import com.ruoyi.common.core.web.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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
    @Operation(summary = "Place an order", description = "Create an order with userId, productId, amount")
    public AjaxResult placeOrder(@Validated @RequestBody PlaceOrderRequest request)
    {
        return orderService.placeOrder(request);
    }

    @GetMapping("/getOrderInfo")
    public AjaxResult getOrderInfo(@Validated @RequestBody PlaceOrderRequest request){
        return orderService.selectOrderList(request) == null ? AjaxResult.error() : AjaxResult.success();
    }

//    @PostMapping("/test1")
//    @Operation(summary = "Place an order", description = "Create an order with userId, productId, amount")
//    public String test1()
//    {
//        // 商品单价10元，库存20个,用户余额50元，模拟一次性购买22个。 期望异常回滚
//        orderService.placeOrder(new PlaceOrderRequest(1L, 1L, 22));
//        return "下单成功";
//    }
}