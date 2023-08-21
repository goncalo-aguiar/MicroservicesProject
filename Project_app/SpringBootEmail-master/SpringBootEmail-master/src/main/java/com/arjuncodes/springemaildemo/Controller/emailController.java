package com.arjuncodes.springemaildemo.Controller;

import com.arjuncodes.springemaildemo.MQConfig.Config;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class emailController {
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public emailController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping("/sendMail/{mail}")
    public String sendEmail(@PathVariable String mail) {
        emailMessage emailMessage = new emailMessage(mail);
        rabbitTemplate.convertAndSend(Config.EXCHANGE,
                Config.ROUTING_KEY, emailMessage);

        return "Email Sent!";
    }
}
