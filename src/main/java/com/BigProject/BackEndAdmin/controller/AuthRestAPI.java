package com.BigProject.BackEndAdmin.controller;

import com.BigProject.BackEndAdmin.messages.request.LoginForm;
import com.BigProject.BackEndAdmin.messages.response.JwtResponse;
import com.BigProject.BackEndAdmin.messages.response.ResponseHandler;
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
    public ResponseEntity<Object> authenticateUser(@RequestBody LoginForm loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtProvider.generateJwtToken(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            if (authentication.isAuthenticated()) {
                return ResponseHandler.generateResponse(0, HttpStatus.OK, new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
            }

            return ResponseHandler.generateResponse(1, HttpStatus.UNAUTHORIZED, null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(2, HttpStatus.MULTI_STATUS, e);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("authenticated")
    public ResponseEntity<Object> authenticatedUser(@AuthenticationPrincipal UserPrinciple userPrinciple) {
        try {
            if (userPrinciple != null) {
                return ResponseHandler.generateResponse(0, HttpStatus.OK, userPrinciple);
            }
            return ResponseHandler.generateResponse(1, HttpStatus.UNAUTHORIZED, null);
        }catch (Exception e){
            return ResponseHandler.generateResponse(2, HttpStatus.MULTI_STATUS, e);
        }
    }

}
