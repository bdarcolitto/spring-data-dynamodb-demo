package com.example.demo;

import java.util.Optional;

import com.example.demo.config.CreateTablesDynamoDB;
import com.example.demo.dto.ProductDTO;
import com.example.demo.extension.DynamoDBServerExtension;
import com.example.demo.model.ProductEntity;
import com.example.demo.service.ProductService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({ SpringExtension.class, DynamoDBServerExtension.class})
@SpringBootTest
@ActiveProfiles(profiles = "test")
public class DynamoSpringDataTest {

    @Autowired
    private CreateTablesDynamoDB createTablesDynamoDB;

    @Autowired
    private ProductService productService;

    @BeforeEach
    void init() {
        createTablesDynamoDB.createTable();
    }

    @Test
    void testSaveProduct() {
        ProductDTO productDTO = new ProductDTO("1", "productName", 2.22f, 20200807);
        productService.save(productDTO);
        final Optional<ProductEntity> productEntity = productService.findById("1");

        Assert.assertFalse(productEntity.isEmpty());
        Assert.assertEquals("productName", productEntity.get().getName());
    }

}
