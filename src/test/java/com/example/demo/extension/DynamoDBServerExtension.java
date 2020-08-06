package com.example.demo.extension;

import com.amazonaws.services.dynamodbv2.local.main.ServerRunner;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class DynamoDBServerExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback,
        BeforeAllCallback {

    private DynamoDBProxyServer server;

    @Override
    public void beforeTestExecution (final ExtensionContext context) throws Exception {
        // Create an in-memory and in-process instance of DynamoDB Local that runs over HTTP
        final String[] localArgs = { "-inMemory", "-port", "8000" }; // <- -port just for knowledge | 8000 alredy is the default port
        // Create an in-memory and in-process instance of DynamoDB Local that skips HTTP
        server = ServerRunner.createServerFromCommandLineArgs(localArgs);
        server.start();
    }

    @Override
    public void afterTestExecution (final ExtensionContext context) throws Exception {
        if (server == null) {
            return;
        }

        try {
            server.stop();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void beforeAll (final ExtensionContext context) throws Exception {
        System.setProperty("sqlite4java.library.path", "build/libs");
    }
}
