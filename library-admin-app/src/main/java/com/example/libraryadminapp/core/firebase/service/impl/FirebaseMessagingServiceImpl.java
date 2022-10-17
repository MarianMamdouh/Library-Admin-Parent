package com.example.libraryadminapp.core.firebase.service.impl;


import com.example.libraryadminapp.core.domain.student.entity.Student;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FirebaseMessagingServiceImpl {

    private final FirebaseMessaging firebaseMessaging;

    public String sendNotification(final String fcmToken, String title, String body) throws FirebaseMessagingException {

        Notification notification = Notification
                .builder()
                .setTitle(title)
                .setBody(body)
                .build();

            Message message = Message
                    .builder()
                    .setToken(fcmToken)
                    .setNotification(notification)
                    .build();
        return firebaseMessaging.send(message);
    }

}