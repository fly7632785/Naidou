package com.itspeed.naidou.model.bean;

/**
 * Created by jafir on 15/9/14.
 */
public class FoodMaterial {
    //种类（猪肉、牛肉）
    private String type;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    //用量
    private String amount;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
