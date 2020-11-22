package com.sd.shop.potatoes.repositories;

import com.sd.shop.potatoes.entities.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
    Iterable<Product> findByNameAndPriceGreaterThan(String name, Double price);

    // Turit rasti pagal pavadinima arba pagal kaina didesne nei arba kiekis didesnis nei
}
