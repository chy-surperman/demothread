package com.data.mappers;

import com.data.entity.gpsData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface insertALL {
   Integer insertGpsData(@Param("vehicleId") String vehicleId,@Param("latitude") String latitude,@Param("longitude") String longitude,@Param("createTime") String createTime,@Param("direction") String direction,@Param("speed") String speed);
}
