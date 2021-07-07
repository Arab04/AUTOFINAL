package com.auto.service.controllers;

import com.auto.service.entity.User;
import com.auto.service.payloads.ApiResponse;
import com.auto.service.payloads.ApiResponseModel;
import com.auto.service.payloads.ReqSignUp;
import com.auto.service.repository.UserRepository;
import com.auto.service.security.AuthService;
import com.auto.service.security.CurrentUser;
import com.auto.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthService authService;


    @GetMapping("/me")
    public HttpEntity<?> getUser(@CurrentUser User user) {
        return ResponseEntity.ok(new ApiResponseModel(user!=null?true:false, user!=null?"Mana user":"Error", user));
    }

    @PreAuthorize("hasAnyRole('ROLE_COORDINATOR','ROLE_ADMIN','ROLE_DIRECTOR')")
    @PostMapping("/register")
    public HttpEntity<?> createUser(@RequestBody ReqSignUp reqUser) {
        ApiResponse response = userService.addUser(reqUser);
        if (response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(response.getMessage(), true));
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(response.getMessage(), false));
    }



    @PreAuthorize("hasAnyRole('ROLE_MANAGER','ROLE_ADMIN','ROLE_DIRECTOR')")
    @GetMapping
    public HttpEntity<?> getUsers() {
        return ResponseEntity.ok(new ApiResponseModel(true, "Mana userlar", userService.getUsers()));
    }

    @PreAuthorize("hasAnyRole('ROLE_MANAGER','ROLE_ADMIN','ROLE_DIRECTOR')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteUser(@PathVariable UUID id) {
        userRepository.deleteById(id);
        return ResponseEntity.ok(new ApiResponse("Deleted", true));
    }

    @PostMapping("firebase/{firebase}")
    public ApiResponse saveFirebase(@CurrentUser User user, @PathVariable String firebase) {
       return userService.saveFirebase(user,firebase);
    }




//    @PutMapping("/edit")
//    public HttpEntity<?> editUser(@RequestBody ReqUser reqUser, @CurrentUser User user) {
//        return userService.editUser(reqUser, user);
//    }


}
