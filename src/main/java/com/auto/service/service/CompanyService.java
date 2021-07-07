package com.auto.service.service;


import com.auto.service.entity.Company;
import com.auto.service.entity.User;
import com.auto.service.entity.enums.Status;
import com.auto.service.payloads.CompanyPayload;
import com.auto.service.payloads.ServiceStatus;
import com.auto.service.repository.UserRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
@Getter
@Setter
public class CompanyService {

    private UserRepository userRepository;

    @Autowired
    public CompanyService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean addInfo(CompanyPayload companies, User user) {
        if(companies != null) {
            Company c = new Company();
            c.setFirmName(companies.getFirmName());
            c.setAddress(companies.getAddress());
            c.setLicense(companies.getLicense());
            c.setCompanyNumber(companies.getPhoneNumber());
            c.setLatitude(companies.getLatitude());
            c.setLongitude(companies.getLongitude());
            user.setCompany(c);
            userRepository.save(user);
            return true;
        }
        else {
            return false;
        }
    }

    public String editService(CompanyPayload companies, User user) {
            Company c1 = new Company();
            c1.setFirmName(companies.getFirmName());
            c1.setAddress(companies.getAddress());
            c1.setLicense(companies.getLicense());
            c1.setCompanyNumber(companies.getPhoneNumber());
            c1.setLatitude(companies.getLatitude());
            c1.setLongitude(companies.getLongitude());
            user.setCompany(c1);
            userRepository.save(user);
            return "Company edited";
    }

    public boolean changeStatus(ServiceStatus serviceStatus, User user) {
        Company c = new Company();
        c.setStatus(Status.ACTIVE);
        user.setCompany(c);
        userRepository.save(user);
        return true;
    }
}
