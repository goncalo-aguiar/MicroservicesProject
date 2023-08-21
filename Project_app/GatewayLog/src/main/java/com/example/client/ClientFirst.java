package com.example.client;

import com.example.domain.UserCredentials;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.http.client.annotation.Client;

import java.util.Map;

import static io.micronaut.http.HttpHeaders.AUTHORIZATION;


@Client("http://localhost:1011/first")
public interface ClientFirst {

    @Post("/log")
    HttpResponse<String> login(@Body UserCredentials userCredentials);

    @Post("/add")
    HttpResponse<?> addUser(@Body UserCredentials userCredentials);

    @Post("/logout")
    HttpResponse<?> logout(@Header(AUTHORIZATION) String authorization);

    @Get("/secured")
    @Consumes(MediaType.TEXT_PLAIN)
    HttpResponse<?> getSecuredData(@Header(AUTHORIZATION) String authorization);

    @Get("/admin")
    @Consumes(MediaType.TEXT_PLAIN)
    HttpResponse<?> getAdmin(@Header(AUTHORIZATION) String authorization);

    @Get("/user")
    @Consumes(MediaType.TEXT_PLAIN)
    HttpResponse<?> getUser(@Header(AUTHORIZATION) String authorization);

    @Get("/info")
    HttpResponse<Map<String, Object>> getUserInfo(@Header(AUTHORIZATION) String jwtToken);

    @Get("/validateToken")
    HttpResponse<Map<String, Object>> validateToken(@Header(AUTHORIZATION) String jwtToken);

}
