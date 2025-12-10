package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.Account;

public interface AccountMapper
{
    Account selectById(Long userId);

    void updateById(Account account);
}