package com.music.server.controller;

import com.music.server.dto.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/config")
public class AdminConfigController {

    @GetMapping
    public ApiResponse<List<Map<String, Object>>> getAllConfig() {
        List<Map<String, Object>> configs = new ArrayList<>();
        return ApiResponse.success(configs);
    }

    @GetMapping("/{key}")
    public ApiResponse<Map<String, Object>> getConfigByKey(@PathVariable String key) {
        Map<String, Object> config = new HashMap<>();
        config.put("key", key);
        config.put("value", "");
        return ApiResponse.success(config);
    }

    @PutMapping
    public ApiResponse<Map<String, Object>> updateConfig(@RequestBody Map<String, Object> request) {
        return ApiResponse.success(request);
    }
}
