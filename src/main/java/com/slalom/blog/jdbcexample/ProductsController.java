package com.slalom.blog.jdbcexample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.List;

@RestController("JdbcProductsController")
@RequestMapping("/jdbc-products")
public class ProductsController {

    @Autowired
    ProductsDao productsDao;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Product findOne(@PathVariable Long id) {
        return this.productsDao.findOne(id);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Product save(@RequestBody Product product) {
        return this.productsDao.save(product);
    }
}
