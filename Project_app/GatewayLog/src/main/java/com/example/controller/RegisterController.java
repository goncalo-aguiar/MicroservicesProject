package com.example.controller;

import com.example.client.ClientFirst;
import com.example.client.MailClient;
import com.example.domain.UserCredentials;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.validation.Validated;
import io.micronaut.views.ModelAndView;
import jakarta.inject.Inject;
import org.apache.commons.validator.routines.EmailValidator;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.net.URI;
import java.util.Map;

@Controller("/api")
public class RegisterController {

    @Inject
    ClientFirst clientFirst;

    @Inject
    MailClient mailClient;

    @Get("/register")
    public ModelAndView registerPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView("register");
        return modelAndView;
    }

    @Post("/add")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Validated
    public Object register(@Body @Valid UserCredentials userCredentials) {

        EmailValidator validator = EmailValidator.getInstance();

        if (!validator.isValid(userCredentials.getEmail())) {
            return new ModelAndView("register", Map.of("errorMessage", "Invalid email address"));
        }

        try {
            HttpResponse<?> response = clientFirst.addUser(userCredentials);
            if (response.getStatus().equals(HttpStatus.OK)) {
                try {
                    HttpResponse<?> mailResponse = mailClient.sendMail(userCredentials.getEmail());

                    if (mailResponse.getStatus().equals(HttpStatus.OK)) {
                        return HttpResponse.redirect(URI.create("/api/login"));
                    } else {
                        return HttpResponse.serverError("Mail service error.");
                    }

                } catch (ConstraintViolationException e) {
                    return HttpResponse.serverError("Mail service error.");
                }
            } else {
                throw new HttpClientResponseException("Bad request", response);
            }

        } catch (HttpClientResponseException e) {
            HttpResponse<?> errorResponse = e.getResponse();
            String errorMessage = errorResponse.getBody(String.class).orElse("An error occurred");
            e.printStackTrace();
            return new ModelAndView("register", Map.of("errorMessage", errorMessage));
        }
    }
}
