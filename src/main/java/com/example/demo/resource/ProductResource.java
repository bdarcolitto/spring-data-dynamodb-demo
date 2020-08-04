package com.example.demo.resource;

import com.example.demo.service.ProductService;
import com.example.demo.vo.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductResource {

    @Autowired
    ProductService productService;

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE) // http://localhost:8080/product/register
    public ResponseEntity<String> save(@RequestBody ProductVO productVO) {
        productService.save(productVO);
        return ResponseEntity.ok("OK");
    }

    @GetMapping(value = "/list", produces = MediaType.TEXT_PLAIN_VALUE) // http://localhost:8080/product/list
    public ResponseEntity<String> findAll() {
        return ResponseEntity.ok( productService.listAll() );
    }
}
