package com.example.shop.controller;

import com.example.shop.entity.Producer;
import com.example.shop.entity.Product;
import com.example.shop.repos.ProducerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Map;

@Controller
@Transactional
@RequestMapping("/producer")
public class ProducerController {
    @Autowired
    private ProducerRepository producerRepository;

    @GetMapping
    public String main(Map<String, Object> model) {
        Iterable<Producer> producers = producerRepository.findAll();

        model.put("producers", producers);
        return "producer/producer";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable Long id, ModelMap model) {

        Producer producer = producerRepository.findById(id).orElseThrow(RuntimeException::new);

        model.put("producer", producer);

        return "producer/info";
    }

    @PostMapping("/add")
    public String add(@RequestParam Long id, @RequestParam String name, @RequestParam String description, Map<String, Object> model) {
        Producer producer = new Producer(id, name, description);

        producerRepository.save(producer);

        Iterable<Producer> producers = producerRepository.findAll();

        model.put("producers", producers);

        return "producer/producer";
    }

    @PostMapping("/filter")
    public String filter(@RequestParam String filter, Map<String, Object> model) {
        Iterable<Producer> producers;

        if (filter != null && !filter.isEmpty()) {
            producers = producerRepository.findByName(filter);
        } else {
            producers = producerRepository.findAll();
        }

        model.put("producers", producers);

        //return "redirect:/product/product";
        return "producer/producer";
    }

    @GetMapping("/add")
    public String add(ModelMap model) {

        model.addAttribute("producer", new Producer());

        return "producer/add";
    }

    @PostMapping("/{id}/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String delete(@PathVariable Long id) {

        producerRepository.deleteById(id);

        return "redirect:/producer";
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String add(@PathVariable Long id, ModelMap model) {

        Producer producer = producerRepository.findById(id).orElseThrow(RuntimeException::new);
        model.addAttribute("producer", producer);
        model.put("producers", producerRepository.findAll());

        return "producer/add";
    }
}