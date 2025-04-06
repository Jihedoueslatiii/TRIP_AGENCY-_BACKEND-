package com.example.hebergement.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;
import java.util.Map;

//@FeignClient(name = "USER") // Vérifie que le port est correct
@FeignClient(name = "USER", url = "http://localhost:8086")
public interface UserClient {

    @GetMapping("/users/{id}")
    Map<String, Object> getUserById(@PathVariable("id") int id);

    @GetMapping("/users/byHebergement/{hebergementId}")
    List<Map<String, Object>> getUsersByHebergement(@PathVariable("hebergementId") Long hebergementId);

    @PostMapping("/users/{userId}/assign/{hebergementId}")
    void assignUserToHebergement(@PathVariable("userId") int userId, @PathVariable("hebergementId") Long hebergementId);

}
