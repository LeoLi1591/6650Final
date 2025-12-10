package com.ruoyi.system.service.impl;

import javax.annotation.Resource;

import com.ruoyi.common.core.web.domain.AjaxResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.ruoyi.system.domain.Account;
import com.ruoyi.system.mapper.AccountMapper;
import com.ruoyi.system.service.AccountService;
import io.seata.core.context.RootContext;

@Service
// non-sharding
@DS("ry-cloud")
// sharding
//@DS("account")
public class AccountServiceImpl implements AccountService
{
    private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Resource
    private AccountMapper accountMapper;


    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    synchronized public Account getAccountByUID(Long userId) {
        if (userId == null) {
            log.error("Invalid id");
            return null;
        }
        return accountMapper.selectById(userId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    synchronized public void reduceBalance(Long userId, Double price)
    {
        if(price < 0){
            log.info("price cannot be negative");
        }
        log.info("=============ACCOUNT START=================");
        log.info("Current XID: {}", RootContext.getXID());

        Account account = accountMapper.selectById(userId);
        Double balance = account.getBalance();
        log.info("UID: {}, Balance {}, Total Price{}", userId, balance, price);

        if (balance < price)
        {
            log.warn("UID {} does not have enough balance，current balance:{}", userId, balance);
            throw new RuntimeException("insufficient balance");
        }
        log.info("Deduct {} balance", userId);
        double currentBalance = account.getBalance() - price;
        account.setBalance(currentBalance);
        accountMapper.updateById(account);
        log.info("UID {} current balance is {}", userId, currentBalance);
        log.info("=============ACCOUNT END=================");
    }

}