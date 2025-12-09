package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.Product;

public interface ProductMapper
{
    public Product selectById(Long productId);

    public void updateById(Product product);
}