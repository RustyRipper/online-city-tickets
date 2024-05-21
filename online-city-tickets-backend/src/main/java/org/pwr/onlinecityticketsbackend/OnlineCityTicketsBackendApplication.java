package org.pwr.onlinecityticketsbackend;

import jakarta.annotation.PostConstruct;

import org.pwr.onlinecityticketsbackend.seeder.DataSeeder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OnlineCityTicketsBackendApplication {

    @Value(value = "${data_seeder.enabled}")
    private boolean enableDataSeeder;

    @Autowired
    private DataSeeder dataSeeder;

    public static void main(String[] args) {
        SpringApplication.run(OnlineCityTicketsBackendApplication.class, args);
    }

    @PostConstruct
    public void seedData() {
        if (enableDataSeeder) {
            dataSeeder.seedData();
        }
    }
}
