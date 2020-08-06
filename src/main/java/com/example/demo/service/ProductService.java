package com.example.demo.service;

import java.util.stream.Collectors;

import com.example.demo.model.ProductEntity;
import com.example.demo.repository.ProductRepository;
import com.example.demo.dto.ProductDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    private ProductRepository productRepository;

    @Autowired
    public ProductService (final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void createTable() {
        productRepository.createTable();
    }

    @Transactional
    public void save(final ProductDTO productDTO) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(productDTO.getId());
        productEntity.setName(productDTO.getName());
        productEntity.setValue(productDTO.getValue());
        productEntity.setCreateDate(productDTO.getCreateDate());
        productRepository.save(productEntity);

        if( productDTO.getId().equals("123") ) {
            throw new RuntimeException("error");
        }
    }

    public String listAll() {
        return productRepository.findAll().stream()
                .map(ProductEntity::toString)
                .collect(Collectors.joining());
    }

    public void listTest() {

        productRepository.findByIdAndName("1", "name");
//        productRepository.findByCreateDate(20200804);
        productRepository.findById("1");
        productRepository.findByIdAndCreateDate("1", 20200804);
    }
}
