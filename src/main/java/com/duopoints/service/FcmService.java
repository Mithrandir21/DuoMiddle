package com.duopoints.service;

import com.duopoints.models.FcmSettings;
import com.duopoints.models.messages.SendMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;

@Service
public class FcmService {

    private final WebClient webClient;
    private final FcmSettings settings;

    public FcmService(FcmSettings settings) {
        this.webClient = WebClient.create();
        this.settings = settings;
    }

    public void send() {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setTo("/");
        sendMessage.setData(Collections.singletonMap("message", "This is a Firebase Cloud Messaging Topic Message!"));

        ClientResponse response = webClient.post()
                .uri("https://fcm.googleapis.com/fcm/send")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "key=" + this.settings.getApiKey())
                .syncBody(sendMessage).exchange().block();

        System.out.println("send: " + response.statusCode());
    }
}
