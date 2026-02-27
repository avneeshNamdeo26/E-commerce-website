package com.productorderingapp.product_service.repository;


import com.productorderingapp.product_service.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
