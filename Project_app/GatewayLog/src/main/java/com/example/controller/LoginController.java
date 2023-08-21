package com.example.controller;

import com.example.client.ClientFirst;
import com.example.domain.UserCredentials;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.http.cookie.Cookie;
import io.micronaut.views.ModelAndView;
import jakarta.inject.Inject;

import java.net.URI;

@Controller("/api")
public class LoginController {

    @Inject
    private ClientFirst clientFirst;

    @Get("/login")
    public ModelAndView loginPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView("login");
        return modelAndView;
    }

    @Post("/log")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public MutableHttpResponse<Object> login(String username, String password) {
        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setUsername(username);
        userCredentials.setPassword(password);

        HttpResponse<String> response = clientFirst.login(userCredentials);
        if (response.getStatus() == HttpStatus.OK) {
            Cookie cookie = Cookie.of("token", response.body());

            // Redirect the response to the /api/user_info endpoint
            String redirectUrl = "/api/home";
            return HttpResponse.redirect(URI.create(redirectUrl)).cookie(cookie);
        } else {
            throw new HttpClientResponseException("Bad request", response);
        }

    }


}
