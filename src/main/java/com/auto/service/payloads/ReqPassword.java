package com.auto.service.payloads;


import lombok.Data;

@Data
public class ReqPassword {
    private String oldPassword;
    private String password;
    private String prePassword;
}
