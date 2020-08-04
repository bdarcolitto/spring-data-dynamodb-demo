package com.example.demo.listener;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded;
import com.amazonaws.services.dynamodbv2.local.main.ServerRunner;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DynamoDBCreate {

    private ProductService productService;

    @Autowired
    public DynamoDBCreate (final ProductService productService) {
        this.productService = productService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void atStart() throws Exception {
        AmazonDynamoDB dynamodb = null;
        try {
            // Create an in-memory and in-process instance of DynamoDB Local that skips HTTP
            dynamodb = DynamoDBEmbedded.create().amazonDynamoDB();
            // use the DynamoDB API with DynamoDBEmbedded
            listTables(dynamodb.listTables(), "DynamoDB Embedded");
        } finally {
            // Shutdown the thread pools in DynamoDB Local / Embedded
            if(dynamodb != null) {
                dynamodb.shutdown();
            }
        }

        // Create an in-memory and in-process instance of DynamoDB Local that runs over HTTP
        final String[] localArgs = { "-inMemory" };
        DynamoDBProxyServer server = null;
        try {
            server = ServerRunner.createServerFromCommandLineArgs(localArgs);
            server.start();

            dynamodb = AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(
                    // we can use any region here
                    new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2"))
                    .build();

            // use the DynamoDB API over HTTP
            listTables(dynamodb.listTables(), "DynamoDB Local over HTTP");
        } finally {
            // Stop the DynamoDB Local endpoint
            //			if(server != null) {
            //				server.stop();
            //			}

            // cria tabela logo depois que o banco sobe
            productService.createTable();
        }
    }

    public static void listTables(ListTablesResult result, String method) {
        System.out.println("found " + Integer.toString(result.getTableNames().size()) + " tables with " + method);
        for(String table : result.getTableNames()) {
            System.out.println(table);
        }
    }

}
