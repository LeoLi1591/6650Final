package com.ruoyi.system.controller;

import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.system.domain.Account;
import com.ruoyi.system.domain.dto.ReduceBalanceRequest;
import com.ruoyi.system.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController
{
    @Autowired
    private AccountService accountService;

    @GetMapping("/getaccountbyid")
    public AjaxResult getAccountByID(@Validated @RequestBody ReduceBalanceRequest request)
    {
        Account account = accountService.getAccountByUID(request.getUserId());
        if (account == null){
            return AjaxResult.error("no such account found, account id = " + request.getUserId());
        }
        return AjaxResult.success(account);
    }

}