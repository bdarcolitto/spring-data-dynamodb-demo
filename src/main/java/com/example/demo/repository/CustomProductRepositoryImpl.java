package com.example.demo.repository;

import java.util.ArrayList;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndex;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.Projection;
import com.amazonaws.services.dynamodbv2.model.ProjectionType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ResourceInUseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomProductRepositoryImpl implements CustomProductRepository{

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomProductRepositoryImpl.class);

    private final DynamoDB dynamoDB;

    public CustomProductRepositoryImpl(DynamoDB dynamoDB) {
        this.dynamoDB = dynamoDB;
    }

    @Override
    public void createTable() {

        try {
            // Attribute definitions
            ArrayList<AttributeDefinition> attributeDefinitions = new ArrayList<>();
            attributeDefinitions.add(new AttributeDefinition()
                    .withAttributeName("id")
                    .withAttributeType("S"));
            attributeDefinitions.add(new AttributeDefinition()
                    .withAttributeName("name")
                    .withAttributeType("S"));
            attributeDefinitions.add(new AttributeDefinition()
                    .withAttributeName("create-date")
                    .withAttributeType("N"));

            // Table key schema
            ArrayList<KeySchemaElement> tableKeySchema = new ArrayList<>();
            tableKeySchema.add(new KeySchemaElement()
                    .withAttributeName("id")
                    .withKeyType(KeyType.HASH));  //Partition key
            tableKeySchema.add(new KeySchemaElement()
                    .withAttributeName("name")
                    .withKeyType(KeyType.RANGE));  //Sort key

            // GSI definition
            GlobalSecondaryIndex gsi = new GlobalSecondaryIndex()
                    .withIndexName("productDateIndex")
                    .withProvisionedThroughput(new ProvisionedThroughput()
                            .withReadCapacityUnits((long) 10)
                            .withWriteCapacityUnits((long) 1))
                    // projection ALL copy all attributes to new gsi "table"
                    .withProjection(new Projection().withProjectionType(ProjectionType.ALL));

            // GSI indexes definitions
            ArrayList<KeySchemaElement> gsiKeySchema = new ArrayList<>();
            gsiKeySchema.add(new KeySchemaElement()
                    .withAttributeName("id")
                    .withKeyType(KeyType.HASH));  //Partition key
            gsiKeySchema.add(new KeySchemaElement()
                    .withAttributeName("create-date")
                    .withKeyType(KeyType.RANGE));  //Sort key

            gsi.setKeySchema(gsiKeySchema);

            // set table name and indexes
            CreateTableRequest createTableRequest = new CreateTableRequest()
                    .withTableName("table-product")
                    .withProvisionedThroughput(new ProvisionedThroughput()
                            .withReadCapacityUnits((long) 5)
                            .withWriteCapacityUnits((long) 1))
                    .withAttributeDefinitions(attributeDefinitions)
                    .withKeySchema(tableKeySchema)
                    .withGlobalSecondaryIndexes(gsi)
                    ;

            Table table = dynamoDB.createTable(createTableRequest);
            table.waitForActive();
            LOGGER.info("Success. Table status: {} ||||||||| Attributes: {}", table.getDescription().getTableStatus(), table.describe());

        } catch (ResourceInUseException e) {
            LOGGER.error("Table already Exists: {}", e.getMessage());
            throw new RuntimeException(e);

        } catch (Exception e) {
            LOGGER.error("Unable to create table: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
