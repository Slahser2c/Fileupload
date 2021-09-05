package com.oocl.fileupload.config;


import com.oocl.fileupload.utlis.IntegrationContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class InititalFlowLoadConfig implements ApplicationRunner {

    @Autowired
    IntegrationContextUtil integrationContextUtil;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        integrationContextUtil.loadMultipleSenderFlow();
    }
}
