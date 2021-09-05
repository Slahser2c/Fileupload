package com.oocl.fileupload.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SenderConfig {

    @Bean
    public List<String> senders(){
        List<String> senders = new ArrayList<>();
        senders.add("ELAND");
        senders.add("TEST");
        return senders;
    }

}
