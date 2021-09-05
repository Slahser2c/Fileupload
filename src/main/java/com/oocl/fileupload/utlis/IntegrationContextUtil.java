package com.oocl.fileupload.utlis;

import com.oocl.fileupload.handler.POVOHandler;
import com.oocl.fileupload.transformer.FileUploadUnmarshalTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.dsl.StandardIntegrationFlow;
import org.springframework.integration.dsl.context.IntegrationFlowContext;
import org.springframework.integration.handler.AbstractMessageHandler;
import org.springframework.integration.mongodb.store.MongoDbChannelMessageStore;
import org.springframework.integration.transformer.Transformer;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Executors;

@Component
public class IntegrationContextUtil {

    @Autowired
    MessageChannel inboundChannel;

    @Autowired
    List<String> senders ;

    @Autowired
    private IntegrationFlowContext flowContext;

    @Autowired
    MongoDbChannelMessageStore mongoDbMessageStore;

    @Autowired
    JAXBUtils jaxbUtils;


    public void loadMultipleSenderFlow() {
        StandardIntegrationFlow integrationFlow =  IntegrationFlows.from(inboundChannel)
                .channel(c->c.executor(Executors.newCachedThreadPool()))
                .route("headers.senderID",mapping -> {
                            for(String sender:senders){
                                mapping.channelMapping(sender,MessageChannels.queue(sender,mongoDbMessageStore,sender).get());
                            }
                        }
                )
                .get();
        flowContext.registration(integrationFlow).id("ftpDispatchFlow").register();

        for(String sender:senders){
            StandardIntegrationFlow senderFlow = IntegrationFlows.from(MessageChannels.queue(sender,mongoDbMessageStore,sender).get())
                    .channel(c->c.executor(Executors.newSingleThreadScheduledExecutor()))
                .transform(messageTransformer())
                .handle(messageHandler())
                    .get();
            flowContext.registration(senderFlow).id(sender+"flow").register();
        }
    }

    public POVOHandler messageHandler(){
        POVOHandler povoHandler = new POVOHandler();
        return povoHandler;
    }

    public FileUploadUnmarshalTransformer messageTransformer(){
        FileUploadUnmarshalTransformer transformer = new FileUploadUnmarshalTransformer(jaxbUtils);
        return transformer;
    }


    public void unregisterFlow(String flowId){
        flowContext.remove(flowId);
    }

}
