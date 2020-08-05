package com.chy.mybatis.entities;

import lombok.Data;

import java.util.Date;

@Data
public class income {

    private String date;
    private float alipay;	//支付宝收入
    private float icCard;     //IC卡收入
    private float xxykt;

}
