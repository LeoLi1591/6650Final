package com.ruoyi.system.service;

public interface ProductService
{
    /**
     * deduct the product stock
     *
     * @param productId
     * @param amount
     * @return
     */
    Double reduceStock(Long productId, Integer amount);
}