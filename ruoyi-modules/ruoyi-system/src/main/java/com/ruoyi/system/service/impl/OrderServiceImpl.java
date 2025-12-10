package com.ruoyi.system.service.impl;

import javax.annotation.Resource;

import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.system.domain.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.ruoyi.system.domain.Order;
import com.ruoyi.system.domain.dto.PlaceOrderRequest;
import com.ruoyi.system.mapper.OrderMapper;
import com.ruoyi.system.service.AccountService;
import com.ruoyi.system.service.OrderService;
import com.ruoyi.system.service.ProductService;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;

@Service
// non-sharding
@DS("ry-cloud")
// sharding
//@DS("order")
public class OrderServiceImpl implements OrderService
{
    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Resource
    private OrderMapper orderMapper;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ProductService productService;

    @Override
    @Transactional
    @GlobalTransactional
    synchronized public AjaxResult placeOrder(PlaceOrderRequest request)
    {
        // validation
        if (request.getUserId() == null || request.getAmount() == null || request.getAmount() < 0){
            log.info("Invalid id :" + request.getUserId());
            return AjaxResult.error("Invalid id");
        }

        if (accountService.getAccountByUID(request.getUserId()) == null){
            log.info("Account not exist: " + request.getUserId());
            return AjaxResult.error("Account not exist");
        }

        if (productService.getProductByProductId(request.getProductId()) == null){
            return AjaxResult.error("no product founded, product Id = "+ request.getProductId());
        }

        log.info("=============ORDER START=================");
        Long userId = request.getUserId();
        Long productId = request.getProductId();
        Integer amount = request.getAmount();
        log.info("GET order request,uid:{}, product_id:{},amount:{}", userId, productId, amount);

        log.info("Current XID: {}", RootContext.getXID());

        Order order = new Order(userId, productId, 0, amount);

        orderMapper.insert(order);
        log.info("Pending-------------");
        // reduce the product stock
        Double totalPrice = productService.reduceStock(productId, amount);
        // deduct the money from user
        accountService.reduceBalance(userId, totalPrice);

        order.setStatus(1);
        order.setTotalPrice(totalPrice);
        orderMapper.updateById(order);
        log.info("Order placed successfully");
        log.info("=============ORDER END=================");

        return AjaxResult.success(order);
    }

}