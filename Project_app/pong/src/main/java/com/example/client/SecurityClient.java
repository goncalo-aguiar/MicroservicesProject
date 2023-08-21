package com.example.client;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.client.annotation.Client;

import java.util.Map;

import static io.micronaut.http.HttpHeaders.AUTHORIZATION;

@Client("http://localhost:1011/first")
public interface SecurityClient {

    @Get("/validateToken")
    HttpResponse<Map<String, Object>> validateToken(@Header(AUTHORIZATION) String jwtToken);
}
