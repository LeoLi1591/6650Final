package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.Order;

public interface OrderMapper
{
    void insert(Order order);

    void updateById(Order order);
}