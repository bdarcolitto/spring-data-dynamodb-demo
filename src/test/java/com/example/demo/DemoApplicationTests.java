package com.example.demo;

import com.example.demo.extension.DynamoDBServerExtension;
import com.example.demo.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({SpringExtension.class, DynamoDBServerExtension.class})
@SpringBootTest
@ActiveProfiles(profiles = "test")
class DemoApplicationTests {

	@Autowired
	private ProductService productService;

	@Test
	void contextLoads() {
		productService.createTable();
	}
}

