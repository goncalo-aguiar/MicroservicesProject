package com.example.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.server.types.files.StreamedFile;
import io.micronaut.views.ModelAndView;

import java.io.InputStream;

@Controller("/api")
public class TicController {

    @Get("/tic")
    public ModelAndView ticPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView("index");
        return modelAndView;
    }

    @Get(value = "/script.js", produces = "application/javascript")
    public HttpResponse<StreamedFile> ticJs() {
        InputStream inputStream = getClass().getResourceAsStream("/views/script.js");
        StreamedFile streamedFile = new StreamedFile(inputStream, MediaType.of("application/javascript"));
        return HttpResponse.ok(streamedFile);
    }
}
