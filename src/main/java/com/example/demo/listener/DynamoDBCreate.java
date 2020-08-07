package com.example.demo.listener;

import com.amazonaws.services.dynamodbv2.local.main.ServerRunner;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import com.example.demo.config.CreateTablesDynamoDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Profile("!test")
@Component
public class DynamoDBCreate {

    private CreateTablesDynamoDB createTablesDynamoDB;

    @Autowired
    public DynamoDBCreate (final CreateTablesDynamoDB createTablesDynamoDB) {
        this.createTablesDynamoDB = createTablesDynamoDB;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void atStart() throws Exception {

        // Create an in-memory and in-process instance of DynamoDB Local that runs over HTTP
        final String[] localArgs = { "-inMemory" };
        DynamoDBProxyServer server = null;
        try {
            server = ServerRunner.createServerFromCommandLineArgs(localArgs);
            server.start();

        } finally {
            createTablesDynamoDB.createTable();
        }
    }
}
