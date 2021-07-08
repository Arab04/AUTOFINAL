package com.auto.service.repository;

import com.auto.service.entity.ServiceEntityProvider;
import com.auto.service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ServiceEntityRepository extends JpaRepository<ServiceEntityProvider, UUID> {

    List<ServiceEntityProvider> findByUser(User user);


    Optional<ServiceEntityProvider> findByPhoneNumber(String phoneNumber);
}
