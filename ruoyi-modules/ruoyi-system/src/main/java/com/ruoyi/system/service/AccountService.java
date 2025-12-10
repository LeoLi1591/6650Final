package com.ruoyi.system.service;

import com.ruoyi.system.domain.Account;

public interface AccountService
{
    /**
     reduce the balance
     */
    void reduceBalance(Long userId, Double price);

    Account getAccountByUID(Long userId);
}