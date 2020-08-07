package com.gps.util;

public class messageResult {
    public static jsonResult success(Object object){
        jsonResult jsonResult = new jsonResult();
        jsonResult.setCode(200);
        jsonResult.setMsg("请求成功");
        jsonResult.setData(object);
        return jsonResult;
    }

    public static  jsonResult fail(Object object){
        jsonResult jsonResult = new jsonResult();
        jsonResult.setCode(400);
        jsonResult.setMsg("请求失败");
        jsonResult.setData(object);
        return jsonResult;
    }
}
