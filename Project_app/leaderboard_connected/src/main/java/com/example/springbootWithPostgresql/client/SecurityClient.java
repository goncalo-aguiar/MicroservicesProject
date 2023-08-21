package com.example.springbootWithPostgresql.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(name = "SecurityClient", url = "http://localhost:1011/first")
public interface SecurityClient {

    @GetMapping("/validateToken")
    ResponseEntity<Map<String, Object>> validateToken(@RequestHeader("Authorization") String jwtToken);

}
