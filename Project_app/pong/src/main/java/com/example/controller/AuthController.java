//AuthController
package com.example.controller;

import com.example.domain.User;
import com.example.domain.UserScoreResponse;
import com.example.repository.UserRepository;
import com.example.service.UserService;

import io.micronaut.http.*;

import io.micronaut.http.annotation.*;

import io.micronaut.http.server.types.files.StreamedFile;
import io.micronaut.views.ModelAndView;
import jakarta.inject.Inject;




import java.io.InputStream;
import java.net.URI;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
public class AuthController {

    @Inject
    private UserService userService;

    @Inject
    private UserRepository userRepository;

    @Get("/test")
    public ModelAndView testPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView("index");
        return modelAndView;
    }

    @Get("/pong")
    @Produces(MediaType.TEXT_HTML)
    public HttpResponse<StreamedFile> pong() {
        InputStream inputStream = getClass().getResourceAsStream("/views/pong.html");
        return HttpResponse.ok(new StreamedFile(inputStream, MediaType.TEXT_HTML_TYPE));
    }


    @Get("/api/bestScore")
    public HttpResponse<?> getBestScore(@QueryValue Long id) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            int bestScore = user.getBestScore();
            return HttpResponse.ok(bestScore);
        } else {
            return HttpResponse.status(HttpStatus.NOT_FOUND);
        }
    }


    @Post(value = "/updateScore", consumes = MediaType.APPLICATION_JSON)
    public HttpResponse<?> updateScore(@Body Map<String, Object> body) {
        Long id = ((Number) body.get("id")).longValue();
        Integer bestScore = (Integer) body.get("bestScore");

        userService.updateUserBestScore(id, bestScore);

        return HttpResponse.ok("Score updated");
    }


}
