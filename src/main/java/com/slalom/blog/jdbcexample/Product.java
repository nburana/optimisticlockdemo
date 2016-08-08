package com.slalom.blog.jdbcexample;

import com.slalom.blog.jdbcexample.optimisticlock.VersionedEntity;

public class Product implements VersionedEntity {
    
    // Table this Entity represents
    private final String TABLE = "product";

    private Long id;
    private Long version;
    private String name;
    private Long upc;

    public Product() {}

    public Product(String name, Long upc) {
        this.name = name;
        this.upc = upc;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUpc() {
        return upc;
    }

    public void setUpc(Long upc) {
        this.upc = upc;
    }

    public String getTableName() {
        return TABLE;
    }
}
