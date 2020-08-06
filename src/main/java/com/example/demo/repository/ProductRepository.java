package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.ProductEntity;
import com.example.demo.model.ProductEntityId;
import org.socialsignin.spring.data.dynamodb.repository.DynamoDBPagingAndSortingRepository;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;

//@EnableScan
public interface ProductRepository extends DynamoDBPagingAndSortingRepository<ProductEntity, ProductEntityId>, CustomProductRepository {

    Optional<ProductEntity> findById(String id);

    Optional<ProductEntity> findByIdAndName(String id, String name);

    @EnableScan
    List<ProductEntity> findAll();

    List<ProductEntity> findByIdAndCreateDate(String id, Integer createDate);





    // this method dont work without @EnableScan because is a range_key
    List<ProductEntity> findByCreateDate(Integer createDate);
}
