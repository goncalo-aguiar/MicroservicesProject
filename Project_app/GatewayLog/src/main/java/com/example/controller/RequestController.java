package com.example.controller;

import com.example.DTO.LeaderboardDTO;
import com.example.client.ClientFirst;
import com.example.client.LeaderboardClient;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.CookieValue;
import io.micronaut.http.annotation.Get;
import io.micronaut.views.ModelAndView;
import jakarta.inject.Inject;

import java.util.HashMap;
import java.util.Map;

@Controller("/api")
public class RequestController {

    @Inject
    ClientFirst clientFirst;

    @Inject
    LeaderboardClient leaderboardClient;


    @Get("/secured")
    public HttpResponse<?> getSecuredData(@CookieValue("token") String jwtToken) {
        if (!jwtToken.isEmpty()) {
            String token = "Bearer " + jwtToken;
            HttpResponse<?> response = clientFirst.getSecuredData(token);
            return response;
        } else {
            return HttpResponse.unauthorized();
        }
    }

    @Get("/admin")
    public HttpResponse<?> getAdmin(@CookieValue("token") String jwtToken) {
        if (!jwtToken.isEmpty()) {
            String token = "Bearer " + jwtToken;
            HttpResponse<?> response = clientFirst.getAdmin(token);
            return response;
        } else {
            return HttpResponse.unauthorized();
        }
    }

    @Get("/user")
    public HttpResponse<?> getUser(@CookieValue("token") String jwtToken) {
        if (!jwtToken.isEmpty()) {
            String token = "Bearer " + jwtToken;
            return clientFirst.getUser(token);
        } else {
            return HttpResponse.unauthorized();
        }
    }

    @Get("/leaderboard")
    public ModelAndView callLead(@CookieValue("token") String jwtToken) {

        jwtToken = "Bearer " + jwtToken;

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView("leaderboard");
        HttpResponse<LeaderboardDTO> response = leaderboardClient.callLeaderboardDto(jwtToken);
        if(response.getStatus() == HttpStatus.OK) {
            LeaderboardDTO leaderboardDTO = response.getBody(LeaderboardDTO.class).get();

            Map<String, Object> model = new HashMap<>();
            model.put("ticTacToeLeaderboard", leaderboardDTO.getTicTacToeLeaderboard());
            model.put("pongLeaderboard", leaderboardDTO.getPongLeaderboard());

            modelAndView.setModel(model);
        }
        return modelAndView;
    }

}
