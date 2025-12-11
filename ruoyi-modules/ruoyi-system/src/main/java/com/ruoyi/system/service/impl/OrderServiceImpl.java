package com.ruoyi.system.service.impl;

import javax.annotation.Resource;

import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.system.domain.Product;
import com.ruoyi.system.ruoyiUtil;
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

import java.time.LocalDate;
import java.util.List;


@Service
@DS("ry-cloud")
public class OrderServiceImpl implements OrderService
{
    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Resource
    private OrderMapper orderMapper;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ruoyiUtil util;

    private Integer LIMIT = 100;
    private Integer OFFSET = 0;

    @Override
    public List<Order> selectOrderList(PlaceOrderRequest request)
    {
        if(StringUtils.isEmpty(request.getQueryStartTime())) {
            log.info("The query start time is empty");
            return null;
        }
        // query the single sharded table base on the query start time

        // we can make the request offsetting control by the front end
        String tableName = util.getWeeklyTableName("p_order", util.parseDate(request.getQueryStartTime()));
        if (!util.checkWeeklyTableExists(tableName)){
            log.info("The table does not exist");
            return null;
        }

        Order order = new Order(request.getUserId(),request.getProductId(), request.getStatus(),request.getQueryEndTime(), request.getAmount());
        List<Order> test_data_show = orderMapper.selectOrderList(tableName,order, LIMIT, OFFSET);
        return test_data_show;


        // Todo: query across multiple tables
    }



    @Override
    @Transactional
    @GlobalTransactional
    public AjaxResult placeOrder(PlaceOrderRequest request)
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


        log.info("Pending-------------");
        // reduce the product stock
        Double totalPrice = productService.reduceStock(productId, amount);
        // deduct the money from user
        accountService.reduceBalance(userId, totalPrice);

        // update the order
        Order order = new Order(userId, productId, "COMPLETE", LocalDate.now().toString(),amount);
        order.setTotalPrice(totalPrice);
        String tableName = util.ensureCurrentWeekTable("p_order");
        orderMapper.insert(tableName,order);

        log.info("Order placed successfully");
        log.info("=============ORDER END=================");

        return AjaxResult.success(order);
    }

}