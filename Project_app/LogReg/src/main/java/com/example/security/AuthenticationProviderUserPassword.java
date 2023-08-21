package com.example.security;

import com.example.domain.Role;
import com.example.domain.User;
import com.example.service.UserService;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationException;
import io.micronaut.security.authentication.AuthenticationProvider;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Singleton
public class AuthenticationProviderUserPassword implements AuthenticationProvider {

    @Inject
    private UserService userService;

    @Override
    public Publisher<AuthenticationResponse> authenticate(HttpRequest<?> httpRequest, AuthenticationRequest<?, ?> authenticationRequest) {
        String username = authenticationRequest.getIdentity().toString();
        String password = authenticationRequest.getSecret().toString();

        return Mono.fromCallable(() -> userService.findByUsername(username))
                .flatMap(userOptional -> {
                    if (userOptional.isPresent()) {
                        User user = userOptional.get();
                        if (Objects.equals(user.getPassword(), password)) {
                            List<String> rolesName = user.getRoles().stream()
                                    .map(Role::getName)
                                    .collect(Collectors.toList());
                            return Mono.just(AuthenticationResponse.success(username, rolesName));
                        }
                    }
                    return Mono.error(AuthenticationResponse.exception());
                })
                .onErrorResume(AuthenticationException.class, Mono::error);
    }
}
