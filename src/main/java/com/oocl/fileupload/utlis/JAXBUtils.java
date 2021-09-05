package com.oocl.fileupload.utlis;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;


public class JAXBUtils implements IMessageParser{

    private static final String JAXB_CONTEXT_DELIM = ":";
    private static JAXBContext jc = null;
    private Properties contextProperties = null;
    private Properties mapperProperties = null;
    private NamespacePrefixMapper mapper = null;
    private static final String CHARSET_UTF8 = "UTF-8";


    public void init() throws Exception{
        Enumeration<String> keyEnum = (Enumeration<String>) contextProperties.propertyNames();
        StringBuilder sb = new StringBuilder();
        while(keyEnum.hasMoreElements()){
            if(sb.length()!=0){
                sb.append(JAXB_CONTEXT_DELIM);
            }
            sb.append(contextProperties.get(keyEnum.nextElement()));
        }
        String contextString = sb.toString();
        jc = JAXBContext.newInstance(contextString);

        mapper = new NamespacePrefixMapperImpl(mapperProperties);
    }

    @Override
    public Object unmarshal(String xmlContent) throws UnmarshalException {
        Object msgObj = null;
        Unmarshaller u = null;
        ByteArrayInputStream bis = null;
        try {
            bis = new ByteArrayInputStream(xmlContent.getBytes(CHARSET_UTF8));
            u = constructUnmarshaller();
            u.setEventHandler(null);
            msgObj = u.unmarshal(bis);

        } catch (Throwable t) {
            throw new UnmarshalException("JAXBMessageParser.unmarshal", t);
        }finally{
            try {
                if(bis!=null)bis.close();
            } catch (IOException e) {
                throw new UnmarshalException("Unable to close input stream",e);
            }
        }
        return msgObj;
    }


    private Unmarshaller constructUnmarshaller() throws Throwable{
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        return unmarshaller;
    }

    public Properties getContextProperties() {
        return contextProperties;
    }

    public void setContextProperties(Properties contextProperties) {
        this.contextProperties = contextProperties;
    }

    public Properties getMapperProperties() {
        return mapperProperties;
    }

    public void setMapperProperties(Properties mapperProperties) {
        this.mapperProperties = mapperProperties;
    }
}
