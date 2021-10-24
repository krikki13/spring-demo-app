package com.kristjan.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    /**
     * Hello world.
     */
    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello world!";
    }
}
