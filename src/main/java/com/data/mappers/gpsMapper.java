package com.data.mappers;


import com.data.entity.gpsData;
import com.data.entity.vehicleGpsDevice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface gpsMapper {
    String selectvehicleId(@Param("terminalPhone") String terminalPhone);
}
