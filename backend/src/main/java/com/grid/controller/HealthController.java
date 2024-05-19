package com.grid.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class HealthController {
    // 测试端口
    @GetMapping("/health")
    ResponseEntity<?> health() {
        return ResponseEntity.noContent().build();
    }
}
