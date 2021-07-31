package com.BigProject.BackEndAdmin.messages.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
    public static ResponseEntity<Object> generateResponse(int resultCode, HttpStatus httpStatus, Object responseObj){
        Map<String, Object> map = new HashMap<>();
        map.put("data", responseObj);
        map.put("resultCode", resultCode);
        map.put("status", httpStatus.value());

        return new ResponseEntity<Object>(map, httpStatus);
    }
}
