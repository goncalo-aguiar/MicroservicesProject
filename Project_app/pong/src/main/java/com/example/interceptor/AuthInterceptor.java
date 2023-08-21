package com.example.interceptor;

import com.example.client.SecurityClient;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.filter.HttpServerFilter;
import io.micronaut.http.filter.ServerFilterChain;
import jakarta.inject.Inject;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Optional;

@Filter("/**")
public class AuthInterceptor implements HttpServerFilter {

    @Inject
    SecurityClient securityClient;


    @Override
    public Publisher<MutableHttpResponse<?>> doFilter(HttpRequest<?> request, ServerFilterChain chain) {

        // skip options request
        if("OPTIONS".equalsIgnoreCase(request.getMethodName())) {
            return chain.proceed(request);
        }

        Optional<String> jwtTokenOptional = Optional.ofNullable(request.getHeaders().get("Authorization"));

        if (jwtTokenOptional.isPresent() && !jwtTokenOptional.get().isEmpty()) {
            String jwtToken = jwtTokenOptional.get();
            jwtToken = jwtToken.replaceFirst("Bearer ", "");

            HttpResponse<Map<String, Object>> validationResponse = securityClient.validateToken(jwtToken);
            if (validationResponse.getStatus().getCode() == HttpStatus.OK.getCode()) {
                Optional<Map<String, Object>> responseBody = validationResponse.getBody();
                if (responseBody.isPresent() && !responseBody.get().containsKey("Error")) {
                    return chain.proceed(request);
                }
            } else {
                return Mono.just(HttpResponse.unauthorized());
            }
        }
        return Mono.just(HttpResponse.unauthorized());
    }
}
