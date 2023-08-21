package com.arjuncodes.springemaildemo.Listener;

import com.arjuncodes.springemaildemo.Controller.emailMessage;
import com.arjuncodes.springemaildemo.EmailSenderService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class emailListener {

    @Autowired
    private EmailSenderService senderService;

    @RabbitListener(queues = "message_queue")
    public void sendEmailMessage(emailMessage email) {

        senderService.sendSimpleEmail(email.getMail(),
                "Welcome to Our Game App"
                ,
                "Dear user,\n" +
                        "\n" +
                        "Thank you for registering with our Game App! We are thrilled to have you on board.\n" +
                        "\n" +
                        "Get ready to experience the excitement of our Pong game and challenge your strategic skills in our Tic Tac Toe game. Play against your friends, climb the leaderboard, and prove your gaming skills.\n" +
                        "\n" +
                        "We value your participation and would like to express our gratitude for joining our community. We hope you have an amazing time playing our games and connecting with other gamers.\n" +
                        "\n" +
                        "Remember, practice makes perfect! So, don't hesitate to dive in and start your gaming journey. If you have any questions or need assistance, feel free to reach out to our support team.\n" +
                        "\n" +
                        "Best regards,");

    }
}
