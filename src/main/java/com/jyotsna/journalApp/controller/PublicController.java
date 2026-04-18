package com.jyotsna.journalApp.controller;

import com.jyotsna.journalApp.entity.User;
import com.jyotsna.journalApp.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {
    private final UserService userService;

    public PublicController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/health-check")
    public String healthCheck(){
        return "OK";
    }
    @PostMapping
    public void cerateUser(@RequestBody User user){
        userService.saveEntry(user);
    }
}
