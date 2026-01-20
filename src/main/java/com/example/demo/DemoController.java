package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    // GET类型HelloWorld接口，需要Spring Security认证
    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }

    // 用户名密码登录接口
    @PostMapping("/login")
    public ResponseEntity<String> login() {
        // 直接返回成功，简化实现
        return ResponseEntity.ok("Login successful");
    }
}
