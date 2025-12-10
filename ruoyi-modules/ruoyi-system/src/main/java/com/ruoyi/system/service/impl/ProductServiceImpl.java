package com.ruoyi.system.service.impl;

import javax.annotation.Resource;

import com.ruoyi.common.core.web.domain.AjaxResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.ruoyi.system.domain.Product;
import com.ruoyi.system.mapper.ProductMapper;
import com.ruoyi.system.service.ProductService;
import io.seata.core.context.RootContext;

@Service
// non-sharding
@DS("ry-cloud")
// sharding
//@DS("product")
public class ProductServiceImpl implements ProductService
{
    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Resource
    private ProductMapper productMapper;


    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    synchronized public Product getProductByProductId(Long productId){
        if (productId == null){
            log.info("productId is null");
            return null;
        }
        return productMapper.selectById(productId);
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    synchronized public Double reduceStock(Long productId, Integer amount)
    {
        log.info("=============PRODUCT START=================");
        log.info("Current XID: {}", RootContext.getXID());

        // Check stock
        Product product = productMapper.selectById(productId);
        if (product == null){
            log.info("The product is not exist product id = {}", productId);
            throw new RuntimeException("The product is not exist, product ID =" + productId);
        }
        Integer stock = product.getStock();
        log.info("Product id {} current stock = {}, purchase amount = {}", productId, stock, amount);

        if (stock < amount)
        {
            log.warn("product id {} insufficient stocks:{}", productId, stock);
            throw new RuntimeException("insufficient stocks");
        }
        log.info("Product id {} price is{}", productId, product.getPrice());
        // 扣减库存
        int currentStock = stock - amount;
        product.setStock(currentStock);
        productMapper.updateById(product);
        double totalPrice = product.getPrice() * amount;
        log.info("Product ID {} current stock is {}, {} total price is {} ", productId, currentStock, amount, totalPrice);
        log.info("=============PRODUCT END=================");
        return totalPrice;
    }

}