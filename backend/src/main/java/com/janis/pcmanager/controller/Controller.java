package com.janis.pcmanager.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class Controller {

    @GetMapping
    public String test() {
        return "sth";
    }

    @GetMapping("/{sth}")
    public String test1(@PathVariable String sth) {
        return sth;
    }
}
