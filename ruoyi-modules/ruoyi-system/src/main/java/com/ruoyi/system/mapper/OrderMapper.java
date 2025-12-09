package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.Order;

public interface OrderMapper
{
    public void insert(Order order);

    public void updateById(Order order);
}