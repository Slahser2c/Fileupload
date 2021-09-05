package com.oocl.fileupload.handler;

import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service
public class MessageStartProcessHandler {
    public Message<?> logInprogress(Message<?> message) {
        System.out.println(Thread.currentThread().getId()+message.getHeaders().get("senderID").toString()+"Processing?");
        return message;
    }
}
