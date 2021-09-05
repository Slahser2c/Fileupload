package com.oocl.fileupload.config;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.connection.ClusterConnectionMode;
import com.mongodb.connection.ClusterType;
import org.bson.UuidRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.util.StringUtils;

import java.util.Arrays;

@Configuration
public class MongoClientConfig extends AbstractMongoClientConfiguration {

    @Value("${oll.mongo.server.host}")
    private String host;

    @Value("${oll.mongo.server.port}")
    private int port;

    @Value("${oll.mongo.username}")
    private String userName;

    @Value("${oll.mongo.password}")
    private String password;

    @Value("${oll.mongo.authenticate.db}")
    private String database;

    @Bean
    @Override
    public MongoClient mongoClient() {
        ServerAddress serverAddress = new ServerAddress(host,port);
        MongoCredential mongoCredential = MongoCredential.createCredential(userName,database,password.toCharArray());
        MongoClientSettings.Builder settingBuilder = MongoClientSettings.builder();
        if(StringUtils.hasText(userName) && StringUtils.hasText(password)){
            settingBuilder =  settingBuilder.credential(mongoCredential);
        }
        MongoClientSettings settings = settingBuilder
                .applyToClusterSettings(builder ->
                        builder.hosts(Arrays.asList(new ServerAddress(host, port)))
                                .mode(ClusterConnectionMode.SINGLE)
                                .requiredClusterType(ClusterType.STANDALONE)
                ).uuidRepresentation(UuidRepresentation.STANDARD).build();
        return MongoClients.create(settings);
    }

    @Override
    protected String getDatabaseName() {
        return database;
    }


}
