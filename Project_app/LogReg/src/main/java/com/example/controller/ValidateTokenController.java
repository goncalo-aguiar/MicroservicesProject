package com.example.controller;

import com.example.utils.CustomException;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.security.token.jwt.validator.JwtTokenValidator;
import jakarta.inject.Inject;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

import static io.micronaut.http.HttpHeaders.AUTHORIZATION;

@Controller("/first")
public class ValidateTokenController {

    @Inject
    private JwtTokenValidator jwtTokenValidator;

    @Get("/validateToken")
    @Secured(SecurityRule.IS_ANONYMOUS)
    public Mono<Map<String, Object>> userInfo(@Header(AUTHORIZATION) String jwtToken, HttpRequest<?> request) {
        if (jwtToken != null && jwtToken.startsWith("Bearer ")) {
            jwtToken = jwtToken.substring(7);
        }

        Map<String, Object> errorInfo = new HashMap<>();
        errorInfo.put("Error" , "Invalid signature");

        return Mono.from(jwtTokenValidator.validateToken(jwtToken, request))
                .map(authentication -> authentication.getAttributes())
                .switchIfEmpty(Mono.error(new CustomException("Invalid signature")))
                .onErrorResume(e -> Mono.just(errorInfo));
    }
}
