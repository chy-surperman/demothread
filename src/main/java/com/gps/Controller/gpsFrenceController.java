package com.gps.Controller;

import com.doudou.gps_coordinate_convert.GPSUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gps.entities.gpsFence;
import com.gps.service.impl.getFenceServiceImpl;
import com.gps.util.jsonResult;
import com.gps.util.messageResult;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/fence")
public class gpsFrenceController {

    @Autowired
    getFenceServiceImpl getFenceServiceimpl;
    @RequestMapping("/lnglat")
    public jsonResult  getFenceData(@RequestBody gpsFence[] data){
        List list = new ArrayList<>();
        JSONArray jsonObject = JSONArray.fromObject(data);
        for (int i=0;i< jsonObject.size();i++) {
            ObjectMapper objectMapper = new ObjectMapper();
            gpsFence gpsFence = objectMapper.convertValue(data[i], gpsFence.class);
            double[] doubles = GPSUtil.gcj02_To_Gps84(gpsFence.getLat(), gpsFence.getLng());
            getFenceServiceimpl.addFence(doubles[0] + "," + doubles[1]);
            list.add(Arrays.asList(doubles[0] + "," + doubles[1]));
        }
       return messageResult.success(JSONArray.fromObject(list));
    }

    @RequestMapping("/dellnglat")
    public jsonResult  delFenceData(){
        Integer integer = getFenceServiceimpl.delFence();
        return messageResult.success(integer);
    }
}
