package com.example.shop.controller;

import com.example.shop.entity.Producer;
import com.example.shop.entity.Product;
import com.example.shop.repos.ProducerRepository;
import com.example.shop.repos.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Map;

@Controller
@Transactional
@RequestMapping("/product")
public class ProductController {
    private final ProductRepository productRepository;
    private final ProducerRepository producerRepository;
    private boolean flagSortName = true;

    @Autowired
    public ProductController(ProductRepository productRepository, ProducerRepository producerRepository) {
        this.productRepository = productRepository;
        this.producerRepository = producerRepository;
    }

    @GetMapping
    public String main(Map<String, Object> model) {
        Iterable<Product> products = productRepository.findAll();

        model.put("products", products);
        return "product/product";
    }

    @GetMapping("/add")
    public String add(ModelMap model) {
        model.put("producers", producerRepository.findAll());
        model.put("products", productRepository.findAll());

        model.addAttribute("product", new Product());

        return "product/add";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable Long id, ModelMap model) {
        Product product = productRepository.findById(id).orElseThrow(RuntimeException::new);

        model.put("producers", producerRepository.findAll());
        model.put("product", product);
        //model.addAttribute("products", product); //

        return "product/info";
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String add(@RequestParam Long id, @RequestParam String name, @RequestParam String description,
                      @RequestParam Integer price, @RequestParam Producer producer, Map<String, Object> model) {
        model.put("producers", producerRepository.findAll());
        Product product = new Product(id, name, description, price, producer);
        productRepository.save(product);
        Iterable<Product> products = productRepository.findAll();
        model.put("products", products);

        return "redirect:/product";
    }

    @PostMapping("/filter")
    public String filter(@RequestParam String filter, Map<String, Object> model) {
        Iterable<Product> products;

        if (filter != null && !filter.isEmpty()) {
            products = productRepository.findByName(filter);
        } else {
            products = productRepository.findAll();
        }
        model.put("products", products);

        return "product/product";
    }


    @GetMapping("/{id}/edit")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String add(@PathVariable Long id, ModelMap model) {
        System.out.println("getEdit");
        Product product = productRepository.findById(id).orElseThrow(RuntimeException::new);
        model.addAttribute("product", product);
        model.put("producers", producerRepository.findAll());
        model.put("products", productRepository.findAll());

        return "product/add";
    }

    @PostMapping("/sort")
    public String sort(@RequestParam String value, Map<String, Object> model) {
        Iterable<Product> products;

        if (flagSortName) //если отсортировано в прямом порядке
            products = productRepository.findAll(Sort.by(value).ascending());
        else
            products = productRepository.findAll(Sort.by(value).descending());

        model.put("products", products);
        flagSortName = !flagSortName;

        //return "redirect:/product/product";
        return "product/product";
    }


    @PostMapping("/{id}/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String delete(@PathVariable Long id) {
        System.out.println("DeleteId");
        productRepository.deleteById(id);

        return "redirect:/product";
    }
}
