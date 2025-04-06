package com.example.user.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

import java.util.List;

@FeignClient(name = "Hebergement")
public interface HerbergementClient {
  /*  @GetMapping("/hebergements/{id}")
    Map<String, Object> getHebergementById(@PathVariable("id") Long id);*/
}
