package com.example.demo.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;

public class ProductEntityId {

    private String id;
    private String name;

    @DynamoDBHashKey
    @DynamoDBIndexHashKey
    public String getId () {
        return id;
    }

    public void setId (final String id) {
        this.id = id;
    }

    @DynamoDBRangeKey
    public String getName () {
        return name;
    }

    public void setName (final String name) {
        this.name = name;
    }

    @Override
    public String toString () {
        final StringBuilder builder = new StringBuilder()//
                .append("ProductEntityId [")//
                .append("id=\"")//
                .append(id).append("\"")//
                .append(",name=\"")//
                .append(name).append("\"")//
                .append("]");
        return builder.toString();
    }
}