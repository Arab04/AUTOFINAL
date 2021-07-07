package com.auto.service.repository;


import com.auto.service.entity.FirebaseToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FirebaseRepository extends JpaRepository<FirebaseToken, Long> {



}
