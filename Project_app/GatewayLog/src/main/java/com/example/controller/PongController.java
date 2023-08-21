package com.example.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.server.types.files.StreamedFile;
import io.micronaut.views.ModelAndView;

import java.io.InputStream;

@Controller("/api")
public class PongController {

    @Get("/pong")
    public ModelAndView pongPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView("pong");
        return modelAndView;
    }

    @Get(value = "/pong.js", produces = "application/javascript")
    public HttpResponse<StreamedFile> pongJs() {
        InputStream inputStream = getClass().getResourceAsStream("/views/pong.js");
        StreamedFile streamedFile = new StreamedFile(inputStream, MediaType.of("application/javascript"));
        return HttpResponse.ok(streamedFile);
    }

}
