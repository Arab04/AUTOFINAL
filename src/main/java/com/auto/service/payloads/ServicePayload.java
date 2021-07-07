package com.auto.service.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServicePayload {

    private String phoneNumber;

    private String password;

    private String name;

    private String serviceType;

    private String number;

    private float lat;

    private float lan;
}
