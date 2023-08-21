package com.example.controller;

import com.example.client.AppClient;
import com.example.domain.User;
import com.example.service.UserService;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.http.cookie.Cookie;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.security.token.jwt.render.BearerAccessRefreshToken;
import jakarta.inject.Inject;
import org.apache.commons.validator.routines.EmailValidator;

import javax.validation.ConstraintViolationException;
import java.text.ParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.micronaut.http.HttpHeaders.AUTHORIZATION;


@Controller("/first")
public class UserController {

    @Inject
    private UserService userService;

    @Inject
    private AppClient appClient;

    @Post("/add")
    @Secured(SecurityRule.IS_ANONYMOUS)
    public HttpResponse<?> addUser(String username, String password,String email) {

        EmailValidator validator = EmailValidator.getInstance();
        if (!validator.isValid(email)) {
            return HttpResponse.badRequest("Invalid email address");
        }

        try {
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPassword(password);
            newUser.setEmail(email);
            userService.register(newUser);

            return HttpResponse.ok();
        } catch (ConstraintViolationException e) {
            return HttpResponse.badRequest("Username already exists");
        }
    }

    @Post("/log")
    @Secured(SecurityRule.IS_ANONYMOUS)
    public HttpResponse<?> login(String username, String password) {
        UsernamePasswordCredentials creds = new UsernamePasswordCredentials(username, password);
        BearerAccessRefreshToken token = appClient.login(creds);

        return HttpResponse.ok(token.getAccessToken());
    }


    @Post("/logout")
    @Secured(SecurityRule.IS_AUTHENTICATED)
    public HttpResponse<?> logout(@Header(AUTHORIZATION) String jwtToken) {
        if (jwtToken != null && !jwtToken.isEmpty()) {
            Cookie clearedCookie = Cookie.of("token", "").maxAge(0);
            return HttpResponse.ok().cookie(clearedCookie);
        } else {
            return HttpResponse.badRequest();
        }
    }

    @Get("/info")
    @Secured(SecurityRule.IS_AUTHENTICATED)
    public HttpResponse<Map<String, Object>> userInfo(@Header(AUTHORIZATION) String jwtToken) {
        try {
            String token = jwtToken.substring(7);

            JWTClaimsSet claims = JWTParser.parse(token).getJWTClaimsSet();
            String username = claims.getSubject();
            List<String> roles = (List<String>) claims.getClaim("roles");

            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("username", username);
            userInfo.put("roles", String.join(", ", roles));

            return HttpResponse.ok(userInfo);
        } catch (ParseException e) {
            return HttpResponse.serverError(Collections.singletonMap("message", "Invalid JWT token"));
        }
    }


    @Get("/secured")
    @Produces(MediaType.TEXT_PLAIN)
    @Secured(SecurityRule.IS_AUTHENTICATED)
    public String hello() {
        return "Hello from secured";
    }

    @Get("/admin")
    @Produces(MediaType.TEXT_PLAIN)
    @Secured("ADMIN")
    public String admin() {
        return "Hello Admin";
    }

    @Get("/user")
    @Produces(MediaType.TEXT_PLAIN)
    @Secured("USER")
    public String user() {
        return "Hello User";
    }


}
