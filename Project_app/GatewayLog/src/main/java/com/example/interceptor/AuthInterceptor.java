package com.example.interceptor;

import com.example.client.ClientFirst;
import io.micronaut.http.*;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.cookie.Cookie;
import io.micronaut.http.filter.HttpServerFilter;
import io.micronaut.http.filter.ServerFilterChain;
import jakarta.inject.Inject;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Filter("/api/**")
public class AuthInterceptor implements HttpServerFilter {

    @Inject
    private ClientFirst clientFirst;

    @Override
    public Publisher<MutableHttpResponse<?>> doFilter(HttpRequest<?> request, ServerFilterChain chain) {
        String requestPath = request.getPath();
        Set<String> excludedPaths = Set.of("/api/login", "/api/log", "/api/register", "/api/add");

        Optional<String> jwtTokenOptional = request.getCookies().findCookie("token").map(Cookie::getValue);

        if (jwtTokenOptional.isPresent() && !jwtTokenOptional.get().isEmpty()) {
            String jwtToken = jwtTokenOptional.get();
            HttpResponse<Map<String, Object>> validationResponse = clientFirst.validateToken(jwtToken);
            if (validationResponse.getStatus().getCode() == HttpStatus.OK.getCode()) {
                if (excludedPaths.contains(requestPath)) {
                    MutableHttpResponse<?> redirectResponse = HttpResponse.redirect(URI.create("/api/home"));
                    redirectResponse.header(HttpHeaders.CACHE_CONTROL, "no-cache");
                    return Mono.just(redirectResponse);
                } else {
                    return chain.proceed(request);
                }
            } else {
                return Mono.just(HttpResponse.unauthorized());
            }
        }

        if (excludedPaths.contains(requestPath)) {
            return chain.proceed(request);
        }
        MutableHttpResponse<?> redirectResponse = HttpResponse.redirect(URI.create("/api/login"));
        redirectResponse.header(HttpHeaders.CACHE_CONTROL, "no-cache");
        return Mono.just(redirectResponse);
    }
}
