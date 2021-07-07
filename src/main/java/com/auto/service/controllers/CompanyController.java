package com.auto.service.controllers;

import com.auto.service.entity.User;
import com.auto.service.payloads.ApiResponse;
import com.auto.service.payloads.CompanyPayload;
import com.auto.service.payloads.ServiceStatus;
import com.auto.service.security.CurrentUser;
import com.auto.service.service.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company")
@AllArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping("/addService")
    public ResponseEntity<?> addService(@RequestBody CompanyPayload companies,@CurrentUser User user) {
        boolean result = companyService.addInfo(companies,user);
        if (result) {
            return ResponseEntity.ok().body(new ApiResponse("Service registered",true));
        }
        else {
            return new ResponseEntity<>(new ApiResponse("Missing info",false), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/editCompany")
    public ResponseEntity<?> editService(@RequestBody CompanyPayload companies, @CurrentUser User user) {
        String result = companyService.editService(companies,user);
            return ResponseEntity.ok().body(result);
    }


    @PutMapping("/edit/status/{id}")
    public ResponseEntity<?> changeStatus(@RequestBody ServiceStatus serviceStatus,@PathVariable("id") Long id,@CurrentUser User user) {
        boolean result = companyService.changeStatus(serviceStatus,user);
        if (result) {
            return ResponseEntity.ok().body(new ApiResponse("Status updated",true));
        }
        else {
            return new ResponseEntity<>(new ApiResponse("FAILURE",false), HttpStatus.BAD_REQUEST);
        }
    }
}
