package com.auto.service.entity;

import com.auto.service.entity.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Company {

    @Column(name = "company_number")
    private String companyNumber;

    @Column(name = "firm_name")
    private String firmName;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "address")
    private String address;

    @Column(name = "license")
    private String license;

    @Column(name = "latitude")
    private Float latitude;

    @Column(name = "longitude")
    private Float longitude;
}
