package com.tripwise.backend.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class HelloController {
    // @RequestMapping("/hello")
    // @RequestMapping("/hello", method=RequestMethod.GET)
    @GetMapping("/hello")
    public String requestMethodName(@RequestParam(required = false, defaultValue = "World") String param) {
        return "Hello " + param;
    }

}