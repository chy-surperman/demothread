package com.gps.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/gps")
public class Gps {
    @RequestMapping("/change")
    public String change() {
        return "gpsChang";
    }


    @RequestMapping("/routeName")
    public String routeName() {
        return "routeName";
    }
}
