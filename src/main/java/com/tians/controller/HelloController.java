package com.tians.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/hello")
public class HelloController {
    @RequestMapping("/mvc")
   // @ResponseBody
    public String hellomethod(){
        return  "home";
    }
}
