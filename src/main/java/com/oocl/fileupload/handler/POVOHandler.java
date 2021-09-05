package com.oocl.fileupload.handler;

import org.springframework.context.annotation.Scope;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.handler.AbstractMessageHandler;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


public class POVOHandler extends AbstractMessageHandler {

    @Override
    @Transactional
    @ServiceActivator
    protected void handleMessageInternal(Message<?> message) {
        System.out.println(Thread.currentThread().getId());
        System.out.println(message.getHeaders().get("history").toString());
        if(message.getHeaders().get("senderID").toString().equals("TEST")){
            try {
                System.out.println(Thread.currentThread().getId()+"TEST!!!");
                Thread.sleep(60000);
                System.out.println(Thread.currentThread().getId()+"TEST DONE!!!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println(Thread.currentThread().getId()+"ELAND!!!");
        }
    }
}
