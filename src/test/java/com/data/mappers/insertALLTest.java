package com.data.mappers;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
class insertALLTest {
    @Autowired
    insertALL insertALL;

    @Test
    public void test(){
        insertALL.insertGpsData("10455","28.187618","113.024592","2020-08-17 09:13:59","279","18");
    }

}
