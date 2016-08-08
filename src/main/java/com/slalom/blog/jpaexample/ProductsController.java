package com.slalom.blog.jpaexample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.ArrayList;

@RestController("JpaProductsController")
@RequestMapping("/jpa-products")
public class ProductsController {
    
    @Autowired
    private ProductsRepository repo;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Product findOne(@PathVariable Long id) {
       return repo.findOne(id);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Product save(@RequestBody Product product) {
       return repo.save(product);
    }
}
