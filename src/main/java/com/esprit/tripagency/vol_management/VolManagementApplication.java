package com.esprit.tripagency.vol_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient

@SpringBootApplication
public class VolManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(VolManagementApplication.class, args);
    }

}
