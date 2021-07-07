package com.auto.service.searchEngine;

import com.auto.service.entity.ServiceEntityProvider;
import com.auto.service.repository.ServiceEntityRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@NoArgsConstructor
public class DataInitializer {

    private ServiceEntityRepository companyRepository;

    @Autowired
    public DataInitializer(ServiceEntityRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    private static boolean initialized = false;

    public void initialize() {
        System.out.println("Initializing Food Truck Data...");
        synchronized (DataInitializer.class) {
            // Continue if not already initialized.
            if (!initialized) {
                List<ServiceEntityProvider> companyList = companyRepository.findAll();

                // Add the received food trucks to our storage
                for (ServiceEntityProvider companies : companyList) {
                    System.err.println(companies);
                    DataAccessor.getInstance().addFoodTruck(companies);
                }

                // set initialized flag true
                initialized = true;
            }

        }

        System.out.println("Food Truck Data Inizialization is Finished!");
    }

}
