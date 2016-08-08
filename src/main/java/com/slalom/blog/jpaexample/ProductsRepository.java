package com.slalom.blog.jpaexample;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ProductsRepository extends CrudRepository<Product, Long> { }
