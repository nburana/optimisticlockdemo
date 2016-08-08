package com.slalom.blog.jdbcexample;

import com.slalom.blog.jdbcexample.optimisticlock.OptimisticlyLocked;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Repository
public class ProductsDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    JdbcTemplate jdbcTemplate;
   
    public Product findOne(Long id) {
        return this.jdbcTemplate.queryForObject("select * from product where id = ?", new Object[]{id},
            new RowMapper<Product>() {
                public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Product product = new Product();
                    product.setId(rs.getLong("id"));
                    product.setVersion(rs.getLong("version"));
                    product.setName(rs.getString("name"));
                    product.setUpc(rs.getLong("upc"));

                    return product;
                }
            });
    }
    
    @OptimisticlyLocked
    public Product save(Product product) {
        this.jdbcTemplate.update("update product set version = ? , name = ?, upc = ? where id = ?",
            product.getVersion(), product.getName(), product.getUpc(), product.getId());

        return this.findOne(product.getId());
    }
}
