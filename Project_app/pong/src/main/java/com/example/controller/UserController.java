//User controller
package com.example.controller;

import com.example.domain.User;
import com.example.repository.UserRepository;
import com.example.service.UserService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;




import java.net.URI;
import java.util.Optional;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@Controller
public class UserController {

    @Inject
    private UserService userService;
    @Inject
    private UserRepository userRepository;




    @Get("/{id}")
    public HttpResponse<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);

        if (user.isPresent()) {
            return HttpResponse.ok(user.get());
        } else {
            return HttpResponse.notFound();
        }
    }

    @Get("/get_user_score/{id}")
    public HttpResponse<?> getUserScore(@PathVariable String id) {
        Optional<User> existingUser = userRepository.findByUsername(id);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            int bestScore = user.getBestScore();
            return HttpResponse.ok(bestScore);
        } else {
            return HttpResponse.ok(0);
        }
    }

    @Post("/update-best-score/{id}/{score}")
    public HttpResponse<?> updateBestScore(@PathVariable String id, @PathVariable Long score) {
        Optional<User> existingUser = userRepository.findByUsername(id);
        System.out.println(id);
        if (existingUser.isPresent()) {
            int currentBestScore = existingUser.get().getBestScore();

            if (score > currentBestScore) {
                userRepository.updateBestScore(id, Math.toIntExact(score));
                return HttpResponse.ok();
            } else {
                return HttpResponse.ok();
            }
        } else {
            User user = new User();
            user.setBestScore(Math.toIntExact(score));
            user.setUsername(id);
            userRepository.save(user);
            return HttpResponse.ok();
        }
    }


}
