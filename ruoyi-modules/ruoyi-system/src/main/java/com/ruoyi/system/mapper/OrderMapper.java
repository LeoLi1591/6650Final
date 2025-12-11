package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper
{
    void insert(@Param("orderTableName") String tableName,
                @Param("order") Order order);
    void updateById(@Param("tableName") String tableName,
                    @Param("order") Order order);

    List<Order> selectOrderList(@Param("orderTableName") String tableName,
                                @Param("order") Order order,
                                @Param("limit") Integer limit,
                                @Param("offset") Integer offset);


}