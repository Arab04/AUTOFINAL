package com.auto.service.payloads;


import lombok.Data;

import java.util.UUID;

@Data
public class ReqSignUp {
    private UUID id;

    private String phoneNumber;

    private String password;

    private String firstName;


}
