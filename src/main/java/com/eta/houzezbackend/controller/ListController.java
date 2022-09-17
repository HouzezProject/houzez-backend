package com.eta.houzezbackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/List")
public class ListController {
    @GetMapping
    public String List(){
        return "list is ";
    }
}
