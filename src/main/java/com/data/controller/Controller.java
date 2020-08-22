package com.data.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.data.entity.gpsData;
import com.data.entity.json;
import com.data.entity.vehicleGpsDevice;
import com.data.mappers.gpsMapper;
import com.data.mappers.insertALL;
import com.data.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@org.springframework.stereotype.Controller
public class Controller {
    @Autowired
    gpsMapper gpsMapper;

    @Autowired
    insertALL insertALL;

    @RequestMapping("/chy")
    public  void test() {
        String fileName = "C:\\Users\\chen\\Desktop/gpsLog.log";
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读一行，读入null时文件结束
            while ((tempString = reader.readLine()) != null) {
              //  System.out.println(tempString.replace(" ,",",").substring(1));
                JSONObject jsonObject = JSONObject.parseObject(tempString.replace(" ,", ",").substring(1));
                System.out.println(jsonObject.getString("latitude")+"-----"+jsonObject.getString("longitude")+"-----"+jsonObject.getString("speed")+"-----"+gpsMapper.selectvehicleId(jsonObject.getString("terminalPhone"))+"-----"+ DateUtil.getDate(jsonObject.getString("time"))+"-----"+jsonObject.getString("direction"));
                insertALL.insertGpsData(gpsMapper.selectvehicleId(jsonObject.getString("terminalPhone")), jsonObject.getString("latitude"), jsonObject.getString("longitude"), jsonObject.getString("time"), jsonObject.getString("direction"), jsonObject.getString("speed"));
                line++;
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
