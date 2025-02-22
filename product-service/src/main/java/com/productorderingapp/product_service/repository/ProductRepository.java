package com.productorderingapp.product_service.repository;


import com.productorderingapp.product_service.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
