package com.afifa.controller;

import com.afifa.service.RedisService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/redis")
public class RedisController {

    private final RedisService redisService;

    public RedisController(RedisService redisService) {
        this.redisService = redisService;
    }

    @PostMapping("/{key}")
    public String set(@PathVariable String key, @RequestParam String value) {
        redisService.setValue(key, value);
        return "Saved to Redis";
    }

    @GetMapping("/{key}")
    public Object get(@PathVariable String key) {
        return redisService.getValue(key);
    }
}