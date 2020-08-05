package com.chy.mybatis.mappers;

import com.chy.mybatis.entities.income;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
class incomeMapperTest {
    @Autowired
    incomeMapper incomeMapper;
    @Test
    public  void test(){
        List<income> incomes = incomeMapper.selectAll();
        System.out.println(incomes.toString());
    }

    @Test
    public  void test1(){
        List selectmoney = incomeMapper.selectmoney();
        System.out.println(selectmoney);
    }
}
