package org.pwr.onlinecityticketsbackend;

import jakarta.annotation.PostConstruct;
import org.pwr.onlinecityticketsbackend.service.DataSeeder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OnlineCityTicketsBackendApplication {

    @Autowired
    private DataSeeder dataSeeder;

    public static void main(String[] args) {
        SpringApplication.run(OnlineCityTicketsBackendApplication.class, args);
    }

    @PostConstruct
    public void seedData() {
        dataSeeder.seedData();
    }
}
