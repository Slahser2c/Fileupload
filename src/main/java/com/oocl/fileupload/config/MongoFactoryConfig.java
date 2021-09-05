package com.oocl.fileupload.config;

import com.mongodb.client.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@Configuration
public class MongoFactoryConfig {

    @Autowired
    MongoClient mongoClient;

    @Bean
    public MongoDatabaseFactory mongoDbFactory(){
        return new SimpleMongoClientDatabaseFactory(mongoClient, "olpmgo");
    }

}
