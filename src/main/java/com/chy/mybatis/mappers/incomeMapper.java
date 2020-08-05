package com.chy.mybatis.mappers;

import com.chy.mybatis.entities.income;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface incomeMapper {
    List<income> selectAll();
    List selectmoney();
}
