package com.auto.service.service;

import com.auto.service.entity.ServiceEntityProvider;
import com.auto.service.entity.User;
import com.auto.service.entity.enums.Status;
import com.auto.service.payloads.ApiResponse;
import com.auto.service.payloads.ServicePayload;
import com.auto.service.repository.ServiceEntityRepository;
import com.auto.service.repository.UserRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@org.springframework.stereotype.Service
@NoArgsConstructor
public class ServiceProvider {


    private ServiceEntityRepository serviceRepository;
    private UserRepository userRepository;
    private PasswordEncoder encoder;

    @Autowired
    public ServiceProvider(ServiceEntityRepository serviceRepository, UserRepository userRepository, PasswordEncoder encoder) {
        this.serviceRepository = serviceRepository;
        this.userRepository = userRepository;
        this.encoder = encoder;
    }


    public ApiResponse setServiceProvider(ServicePayload payload,User user) {
        ServiceEntityProvider service = new ServiceEntityProvider();

        service.setPhoneNumber(payload.getPhoneNumber());
        service.setPassword(encoder.encode(payload.getPassword()));
        service.setFirstName(payload.getName());
        service.setNumber(payload.getNumber());
        service.setServiceType(payload.getServiceType());
        service.setStatus(Status.ACTIVE);
        service.setLan(payload.getLan());
        service.setLat(payload.getLat());
        System.err.println(user.getId());
        service.setUser(user);
        serviceRepository.save(service);
//        jdbcTemplate.update("insert into service_entity_provider () values ()")

        List<ServiceEntityProvider> serviceList = user.getServiceList();
        serviceList.add(service);
        userRepository.save(user);

        return new ApiResponse("Service Registered",true);
    }

}
