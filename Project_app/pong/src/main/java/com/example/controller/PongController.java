package com.example.controller;

//import com.example.client.TestClient;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.types.files.StreamedFile;
import jakarta.inject.Inject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Controller
public class PongController {

    /*@Inject
    private TestClient testClient;*/

    @Get(value = "/index", produces = MediaType.TEXT_HTML)
    public HttpResponse<StreamedFile> index() {
        InputStream inputStream = getClass().getResourceAsStream("/views/index.html");
        StreamedFile streamedFile = new StreamedFile(inputStream, MediaType.TEXT_HTML_TYPE);
        return HttpResponse.ok(streamedFile);
    }

//    @Get(value = "/index")
//    @Produces(MediaType.TEXT_HTML)
//    public HttpResponse<String> index() throws IOException {
//        String htmlContent = new String(getClass().getResourceAsStream("/views/index.html").readAllBytes(), StandardCharsets.UTF_8);
//        return HttpResponse.ok(htmlContent);
//    }

    @Get(value = "/pong.js", produces = "application/javascript")
    public HttpResponse<StreamedFile> pongJs() {
        InputStream inputStream = getClass().getResourceAsStream("/views/pong.js");
        StreamedFile streamedFile = new StreamedFile(inputStream, MediaType.of("application/javascript"));

        return HttpResponse.ok(streamedFile);
    }
    @Get(value = "/pong.html", produces = MediaType.TEXT_HTML)
    public HttpResponse<StreamedFile> pongHtml() {
        InputStream inputStream = getClass().getResourceAsStream("/views/pong.html");
        StreamedFile streamedFile = new StreamedFile(inputStream, MediaType.TEXT_HTML_TYPE);
        return HttpResponse.ok(streamedFile);
    }
}
