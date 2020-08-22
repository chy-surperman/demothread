package com.data.controller;

import com.alibaba.fastjson.JSONObject;
import com.data.mappers.gpsMapper;
import com.data.mappers.insertALL;
import com.data.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.unit.DataUnit;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;

@org.springframework.stereotype.Controller
public class buildController {
    @Autowired
    com.data.mappers.gpsMapper gpsMapper;
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;
    @Autowired
    com.data.mappers.insertALL insertALL;
    @RequestMapping("hasReturn")
    public void hasReturn() throws ExecutionException, InterruptedException {
//        taskExecutor.execute(()->{
//            //创建runnnable实例
//            TicketRunnable run1=new TicketRunnable();
//            TicketRunnable run2=new TicketRunnable();
//            //创建Thread实例并将runnable实例放入
//            Thread th1=new Thread(run1,"th1");
//            Thread th2=new Thread(run2,"th2");
//            //通过线程实例控制线程的行为(运行、停止)
//            th1.start();
//            th2.start();
//        });
    }
    @RequestMapping("/test")
    public  void test() {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        String fileName = "C:\\Users\\chen\\Desktop/gpsLog.log.2020-08-17";
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读一行，读入null时文件结束
            while ((tempString = reader.readLine()) != null) {
//                System.out.println(tempString.substring(0,19));
//                System.out.println(tempString.substring(69));
                Date stand= DateUtil.getDate(tempString.substring(0, 19));
                Date start= DateUtil.getDate("2020-08-17 09:12:35");
                Date end= DateUtil.getDate("2020-08-17 22:32:17");
                if (stand.compareTo(start)==1 && end.compareTo(stand)==1 ){
               //     System.out.println(tempString.substring(0, 19)+"==="+tempString.substring(69));
                    JSONObject jsonObject = JSONObject.parseObject(tempString.substring(69));
                    if (jsonObject.getString("latitude").length()>=2 && jsonObject.getString("longitude").length()>=3){
                        String latitude1 = jsonObject.getString("latitude").substring(0,2) + "." + jsonObject.getString("latitude").substring(2);
                        String longitude1 = jsonObject.getString("longitude").substring(0,3) + "." + jsonObject.getString("longitude").substring(3);
                        String date = tempString.substring(0, 19);
                        executorService.submit(()->{
                            System.out.println("开始插入数据"+Thread.currentThread().getName()+gpsMapper.selectvehicleId(jsonObject.getString("terminalPhone"))+"==="+latitude1+"==="+longitude1+"==="+date+"==="+jsonObject.getString("direction")+"==="+jsonObject.getString("speed").substring(0,2));
                         insertALL.insertGpsData(gpsMapper.selectvehicleId(jsonObject.getString("terminalPhone")),latitude1,longitude1,date,jsonObject.getString("direction"),jsonObject.getString("speed").substring(0,2));
                        });
                    }
                    //                    insertALL.insertGpsData(gpsMapper.selectvehicleId(jsonObject.getString("terminalPhone")),jsonObject.getString("latitude"),jsonObject.getString("longitude"),tempString.substring(0, 19),jsonObject.getString("direction"),jsonObject.getString("speed"));
               }
//                JSONObject jsonObject = JSONObject.parseObject(tempString.replace(" ,", ",").substring(1));
//                System.out.println(jsonObject.getString("latitude")+"-----"+jsonObject.getString("longitude")+"-----"+jsonObject.getString("speed")+"-----"+gpsMapper.selectvehicleId(jsonObject.getString("terminalPhone"))+"-----"+ DateUtil.getDate(jsonObject.getString("time"))+"-----"+jsonObject.getString("direction"));
//                insertALL.insertGpsData(gpsMapper.selectvehicleId(jsonObject.getString("terminalPhone")),jsonObject.getString("latitude"),jsonObject.getString("longitude"),jsonObject.getString("time"),jsonObject.getString("direction"),jsonObject.getString("speed"));
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

