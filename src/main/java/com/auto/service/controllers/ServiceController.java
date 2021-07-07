package com.auto.service.controllers;

import com.auto.service.entity.User;
import com.auto.service.payloads.ApiResponse;
import com.auto.service.payloads.ServicePayload;
import com.auto.service.security.CurrentUser;
import com.auto.service.service.ServiceProvider;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service")
@AllArgsConstructor
public class ServiceController {

    private final ServiceProvider serviceProvider;

    @PostMapping("/setService")
    public ResponseEntity<?> setCompany(@RequestBody ServicePayload payload, @CurrentUser User user) {
        ApiResponse response = serviceProvider.setServiceProvider(payload,user);
        if(response.isSuccess()) {
            return ResponseEntity.ok().body(new ApiResponse("Service Registered",true));
        }
        else {
            return new ResponseEntity(new ApiResponse("FAILURE",false), HttpStatus.CONFLICT);
        }
    }
}
