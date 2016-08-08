package com.slalom.blog.jdbcexample.optimisticlock;

public class OptimisticLockException extends RuntimeException { 
    
    public OptimisticLockException(String description) {
        super(description);
    }
}
