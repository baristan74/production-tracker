package com.dev.productionTracker.controller;

import com.dev.productionTracker.dto.AuthRequest;
import com.dev.productionTracker.dto.CreateUserRequest;
import com.dev.productionTracker.model.User;
import com.dev.productionTracker.security.JwtAuthFilter;
import com.dev.productionTracker.service.JwtService;
import com.dev.productionTracker.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/addNewUser")
    public ResponseEntity<?> addNewUser(@RequestBody CreateUserRequest createUserRequest) {
        return ResponseEntity.ok(userService.createUser(createUserRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok(userService.login(authRequest));
    }
}
