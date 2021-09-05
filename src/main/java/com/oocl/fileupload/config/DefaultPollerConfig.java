package com.oocl.fileupload.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.PollerSpec;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;

@Configuration
public class DefaultPollerConfig {

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerSpec poller(){
        return Pollers.fixedDelay(1).maxMessagesPerPoll(2);
    }

}
