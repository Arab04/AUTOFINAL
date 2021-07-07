package com.auto.service.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kill")
public class KillController {

    @GetMapping
    public String kill() {
        System.exit(0);
        return "ok";
    }
}
