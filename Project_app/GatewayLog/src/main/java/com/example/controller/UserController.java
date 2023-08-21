package com.example.controller;

import com.example.client.ClientFirst;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.views.ModelAndView;
import jakarta.inject.Inject;

import java.util.*;

@Controller("/api")
public class UserController {

    @Inject
    ClientFirst clientFirst;

    @Get("/home")
    public ModelAndView userInfoNew(@CookieValue("token") String jwtToken) {
        if (!jwtToken.isEmpty()) {
            String token = "Bearer " + jwtToken;
            HttpResponse<Map<String, Object>> response = clientFirst.getUserInfo(token);
            if (response.getStatus().equals(HttpStatus.OK)) {
                Map<String, Object> userInfo = response.getBody().orElse(Collections.emptyMap());
                return new ModelAndView("home", userInfo);
            } else {
                return new ModelAndView("error", Collections.singletonMap("message", "Error fetching user information"));
            }
        } else  {
            return new ModelAndView("error", Collections.singletonMap("message", "No JWT token found"));
        }
    }

    @Post("/logout")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public HttpResponse<?> logout(@CookieValue("token") String jwtToken) {
        String token = "Bearer " + jwtToken;
        HttpResponse<?> response = clientFirst.logout(token);

        return response;
    }
}
