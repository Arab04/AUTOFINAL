package com.auto.service.payloads;


import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ResUser {
    private UUID id;
    private String phoneNumber;
    private String password;
    private String firstName;
    private List<String> roleNameList;
}
