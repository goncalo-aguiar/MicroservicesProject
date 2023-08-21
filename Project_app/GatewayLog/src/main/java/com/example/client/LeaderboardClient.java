package com.example.client;

import com.example.DTO.LeaderboardDTO;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.client.annotation.Client;

import static io.micronaut.http.HttpHeaders.AUTHORIZATION;

@Client("http://localhost:9191/")
public interface LeaderboardClient {

    @Get("/leaderboard")
    HttpResponse<LeaderboardDTO> callLeaderboardDto(@Header(AUTHORIZATION) String jwtToken);;

}
