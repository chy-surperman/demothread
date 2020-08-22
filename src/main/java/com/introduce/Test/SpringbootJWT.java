package com.introduce.Test;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.awt.datatransfer.StringSelection;

@RestController
public class SpringbootJWT {
    @RequestMapping("/test/test")
    public  String test(String username, HttpServletRequest request){
        request.getSession().setAttribute("username",username);
        return  "ok ";
    }
}
