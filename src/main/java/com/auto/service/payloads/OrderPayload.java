package com.auto.service.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderPayload {

    private UUID companyId;
    private String desc;
    private float lat;
    private float lan;
    private String serviceLocation;
}
