package com.example.jwt.controller;

import com.example.jwt.dto.UserDto;
import com.example.jwt.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import static com.example.jwt.util.Constants.*;

@RestController
@RequestMapping(API)
public class UserController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping(PING)
    public String ping() {
        return "PONG";
    }

    @PostMapping(TOKEN)
    public String generateToken(@RequestBody UserDto userDto) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword()));
        } catch (Exception e) {
            throw new Exception("invalid username or password");
        }
        return jwtUtil.generateToken(userDto.getUsername());
    }
}
