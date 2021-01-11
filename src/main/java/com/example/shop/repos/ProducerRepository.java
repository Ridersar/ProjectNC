package com.example.shop.repos;

import com.example.shop.entity.Producer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProducerRepository extends CrudRepository<Producer, Long> {
    List<Producer> findByName(String name);
    //Producer findById(Long id);
    void deleteById(Long id);
}
