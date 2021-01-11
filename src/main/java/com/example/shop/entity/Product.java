package com.example.shop.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @Size(min=3, max=30)
    private String name;

    @Column(name = "description")
    @Size(min=10, max=250)
    private String description;

    @Column(name = "price")
    private Integer price;

    @ManyToOne
    @JoinColumn(name = "producer")
    private Producer producer;

    public Product() {
    }

    public Product(Long id, String name, String description, Integer price, Producer producer) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.producer = producer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }
}
