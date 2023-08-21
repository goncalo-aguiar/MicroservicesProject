package com.example.controller;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;

@Secured(SecurityRule.IS_ANONYMOUS)
@Controller
public class HomeControllerTest {

    @Get
    @Produces(MediaType.TEXT_PLAIN)
    public String hellotest() {
        return "Hello unsecured";
    }
}
