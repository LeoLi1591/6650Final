package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.Product;

public interface ProductMapper
{
    Product selectById(Long productId);

    void updateById(Product product);
}