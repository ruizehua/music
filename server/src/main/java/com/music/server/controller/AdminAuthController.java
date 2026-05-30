package com.music.server.controller;

import com.music.server.dto.ApiResponse;
import com.music.server.service.AdminAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/auth")
public class AdminAuthController {

    @Autowired
    private AdminAuthService adminAuthService;

    @Value("${admin.username}")
    private String adminUsername;

    @PostMapping("/login")
    public ApiResponse<Map<String, Object>> login(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");
        
        if (username == null || username.trim().isEmpty()) {
            return ApiResponse.error(400, "用户名不能为空");
        }
        
        if (password == null || password.trim().isEmpty()) {
            return ApiResponse.error(400, "密码不能为空");
        }
        
        String token = adminAuthService.login(username.trim(), password);
        
        if (token == null) {
            return ApiResponse.error(401, "用户名或密码错误");
        }
        
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("username", adminUsername);
        userInfo.put("role", "admin");
        
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("userInfo", userInfo);
        
        return ApiResponse.success(result);
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout() {
        adminAuthService.logout();
        return ApiResponse.success("登出成功", null);
    }

    @GetMapping("/info")
    public ApiResponse<Map<String, Object>> getUserInfo(@RequestHeader(value = "Authorization", required = false) String token) {
        if (token == null || token.isEmpty()) {
            return ApiResponse.error(401, "未登录");
        }
        
        if (!adminAuthService.validateToken(token)) {
            return ApiResponse.error(401, "Token无效或已过期");
        }
        
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("username", adminUsername);
        userInfo.put("role", "admin");
        
        return ApiResponse.success(userInfo);
    }
}
