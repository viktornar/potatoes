package com.sd.shop.potatoes.repositories;

import com.sd.shop.potatoes.entities.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
    Iterable<Product> findByNameAndPriceGreaterThan(String name, Double price);

    Iterable<Product> findByNameOrPriceGreaterThanOrCountGreaterThan(String name, Double price, int count);
}
