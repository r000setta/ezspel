package com.jqy.ezspel.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public String test(@RequestParam(value = "a") String a){
        System.out.println(a);
        return a;
    }
}
