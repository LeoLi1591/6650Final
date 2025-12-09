package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.Account;

public interface AccountMapper
{
    public Account selectById(Long userId);

    public void updateById(Account account);
}