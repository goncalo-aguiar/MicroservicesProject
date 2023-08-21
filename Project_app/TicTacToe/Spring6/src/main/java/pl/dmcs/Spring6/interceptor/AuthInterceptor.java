package pl.dmcs.Spring6.interceptor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.server.ResponseStatusException;
import pl.dmcs.Spring6.client.SecurityClient;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Component
public class AuthInterceptor implements Filter {

    final
    SecurityClient securityClient;

    public AuthInterceptor(SecurityClient securityClient) {
        this.securityClient = securityClient;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        if ("OPTIONS".equalsIgnoreCase(httpRequest.getMethod())) {
            chain.doFilter(request, response);
            return;
        }

        String authHeader = httpRequest.getHeader("Authorization");

        Optional<String> jwtTokenOptional = Optional.ofNullable(authHeader);
        if (jwtTokenOptional.isPresent() && !jwtTokenOptional.get().isEmpty()) {
            String jwtToken = jwtTokenOptional.get();
            jwtToken = jwtToken.replaceFirst("Bearer ", "");
            ResponseEntity<Map<String, Object>> validationResponse = securityClient.validateToken(jwtToken);
            if (validationResponse.getStatusCode() == HttpStatus.OK) {

                Map<String, Object> responseBody = validationResponse.getBody();
                if (responseBody != null && !responseBody.containsKey("Error")) {
                    chain.doFilter(request, response);
                    return;
                }
            }
        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
    }


}
