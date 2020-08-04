package com.example.demo.service;

import java.util.stream.Collectors;

import com.example.demo.model.ProductEntity;
import com.example.demo.repository.ProductRepository;
import com.example.demo.vo.ProductVO;
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
    public void save(final ProductVO productVO) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(productVO.getId());
        productEntity.setName(productVO.getName());
        productEntity.setValue(productVO.getValue());
        productEntity.setCreateDate(productVO.getCreateDate());
        productRepository.save(productEntity);

        productRepository.findById(productVO.getId());
//        productRepository.findByCreateDate(productVO.getCreateDate());

        if( productVO.getId().equals("123") ) {
            throw new RuntimeException("error");
        }
    }

    public String listAll() {
        return productRepository.findAll().stream()
                .map(ProductEntity::toString)
                .collect(Collectors.joining());
    }
}
