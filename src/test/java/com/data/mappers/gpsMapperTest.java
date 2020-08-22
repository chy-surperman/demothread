package com.data.mappers;

import com.data.entity.gpsData;
import com.data.entity.vehicleGpsDevice;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
class gpsMapperTest {
    @Autowired
    gpsMapper gpsMapper;
    @Test
    public void test(){
       String gpsData = gpsMapper.selectvehicleId("19042395090");
        System.out.println(gpsData);
    }
}
