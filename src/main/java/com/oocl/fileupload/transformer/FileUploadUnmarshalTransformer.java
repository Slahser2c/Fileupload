package com.oocl.fileupload.transformer;

import com.oocl.fileupload.utlis.JAXBUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.integration.transformer.AbstractTransformer;
import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.Map;


public class FileUploadUnmarshalTransformer implements GenericTransformer<Message<?>, Message<?>> {

    private JAXBUtils jaxbUtils;

    public FileUploadUnmarshalTransformer(JAXBUtils jaxbUtils){
        this.jaxbUtils = jaxbUtils;
    }

    @Override
    public Message<?> transform(Message<?> message) {
        System.out.println("transformer: "+Thread.currentThread().getId());
        System.out.println(message.getHeaders().toString());
        try {
            jaxbUtils.init();
            Object payload = jaxbUtils.unmarshal(message.getPayload().toString());
            Map<String, Object> headerMap = message.getHeaders();
            return MessageBuilder.withPayload(payload)
                    .copyHeaders(headerMap).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }
}
