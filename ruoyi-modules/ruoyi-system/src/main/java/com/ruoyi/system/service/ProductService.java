package com.ruoyi.system.service;

import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.system.domain.Product;

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

    Product getProductByProductId(Long productId);
}