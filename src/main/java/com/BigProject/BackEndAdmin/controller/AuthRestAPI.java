package com.BigProject.BackEndAdmin.controller;

import com.BigProject.BackEndAdmin.messages.request.LoginForm;
import com.BigProject.BackEndAdmin.messages.response.JwtResponse;
import com.BigProject.BackEndAdmin.security.jwt.JwtProvider;
import com.BigProject.BackEndAdmin.security.services.UserPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/")
public class AuthRestAPI {

    private final AuthenticationManager authenticationManager;

    private final JwtProvider jwtProvider;

    @Autowired
    public AuthRestAPI(AuthenticationManager authenticationManager, JwtProvider jwtProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("login")
    public ResponseEntity<Map<Object, Object>> authenticateUser(@RequestBody LoginForm loginRequest) {
        Map<Object, Object> response = new HashMap<>();
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        if (authentication.isAuthenticated()) {
            response.put("currentUser", new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
            response.put("resultCode", 0);

            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        response.put("resultCode", 1);
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("authenticated")
    public ResponseEntity<Map<Object, Object>> authenticatedUser(@AuthenticationPrincipal UserPrinciple userPrinciple) {
        Map<Object, Object> response = new HashMap<>();

        if (userPrinciple != null) {
            response.put("data", userPrinciple);
            response.put("resultCode", 0);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        response.put("resultCode", 1);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

}
