package com.tripwise.backend.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class HelloController {
    @GetMapping("/")
    public String helloWorld(@RequestParam(required = false, defaultValue = "World") String param) {
        return "Hello " + param;
    }
    @GetMapping("/hello")
    public String helloWorld2(@RequestParam(required = false, defaultValue = "World") String param) {
        return "Hello " + param;
    }

}