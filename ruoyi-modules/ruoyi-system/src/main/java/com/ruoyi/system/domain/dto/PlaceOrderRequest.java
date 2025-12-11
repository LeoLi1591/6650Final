package com.ruoyi.system.domain.dto;

public class PlaceOrderRequest
{
    private Long userId;

    private Long productId;

    private Integer amount;

    private String queryStartTime;
    private String queryEndTime;
    private String status;

    // pagination
    // ruoyi has it's on the front end.
    private int offsetting;  // We can make this front end control for instance 0~19, 20 ~ 39

    public PlaceOrderRequest()
    {
    }

    public PlaceOrderRequest(Long userId, Long productId, Integer amount)
    {
        this.userId = userId;
        this.productId = productId;
        this.amount = amount;
    }
    public int getOffsetting() {
        return offsetting;
    }

    public String getStatus() {
        return status;
    }
    public String getQueryStartTime() {
        return queryStartTime;
    }

    public String getQueryEndTime() {
        return queryEndTime;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getProductId()
    {
        return productId;
    }

    public void setProductId(Long productId)
    {
        this.productId = productId;
    }

    public Integer getAmount()
    {
        return amount;
    }

    public void setAmount(Integer amount)
    {
        this.amount = amount;
    }
}