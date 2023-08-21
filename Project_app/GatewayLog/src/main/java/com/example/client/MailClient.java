package com.example.client;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;

@Client("http://localhost:7777")
public interface MailClient {
    @Post("/sendMail/{email}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Header(name = "API-KEY", value = "789s5afs8h8as1as578aw65yh5fd7qt")
    HttpResponse<?> sendMail(@PathVariable String email);

}
