package com.auto.service.projection;


import com.auto.service.entity.Role;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "customRole", types = {Role.class})
public interface CustomRole {
    Integer getId();
    String getName();

}
