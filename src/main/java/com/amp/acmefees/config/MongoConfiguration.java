package com.amp.acmefees.config;

import com.mongodb.MongoClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Stream;

@Configuration
@EnableMongoRepositories(basePackages = "com.amp.acmefees.repository")
public class MongoConfiguration extends AbstractMongoConfiguration {
    @Override
    public MongoClient mongoClient() {
        // TODO Properly use configuration
        return new MongoClient("localhost", 27018);
    }

    @Override
    protected String getDatabaseName() {
        return "fees";
    }

    @Override
    protected Collection<String> getMappingBasePackages() {
        Package mappingBasePackage = getClass().getPackage();
        return Collections.singleton(mappingBasePackage == null ? null : mappingBasePackage.getName());
    }
}
