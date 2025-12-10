package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.Order;
import org.apache.ibatis.annotations.Param;

public interface OrderMapper
{
    void insert(@Param("orderTableName") String tableName,
                @Param("order") Order order);
    void updateById(@Param("tableName") String tableName,
                    @Param("order") Order order);
}