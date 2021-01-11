package com.example.shop.repos;

import com.example.shop.entity.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {
    List<Product> findByName(String name);
    //Product findById(Long id);
    //void deleteById(Long id);
    Iterable<Product> findAll(Sort sort);
}
