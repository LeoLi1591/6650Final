package com.ruoyi.system.controller;

import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.system.domain.Account;
import com.ruoyi.system.domain.Product;
import com.ruoyi.system.domain.dto.ReduceBalanceRequest;
import com.ruoyi.system.domain.dto.ReduceStockRequest;
import com.ruoyi.system.service.AccountService;
import com.ruoyi.system.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController
{
    @Autowired
    private ProductService productService;

    @GetMapping("/getproductbyid")
    public AjaxResult getProductByID(@Validated @RequestBody ReduceStockRequest request)
    {
        Product product = productService.getProductByProductId(request.getProductId());
        if (product == null){
            return AjaxResult.error("no product founded, product Id = "+ request.getProductId());
        }
        return AjaxResult.success(product);
    }

}