package com.auto.service.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@NoArgsConstructor
@Getter
@Setter
public class CompanyPayload {


    private String phoneNumber;

    private String firmName;

    private String address;

    private String license;

    private float latitude;

    private float longitude;
}
