package com.breaze.genesis.controllers;

import com.breaze.genesis.dtos.UserResponse;
import com.breaze.genesis.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getProfile(@PathVariable Long id) {
        System.out.println("ENTRÓ AL ENDPOINT ID = " + id);
        return ResponseEntity.ok(userService.getProfileById(id));
    }
}