package com.slalom.blog.jdbcexample.optimisticlock;

import java.lang.annotation.*;

/**
 * Marks an Entity for optimistic locking.
 * Entities must implment com.slalom.blog.jdbcexample.optimisticlock.VersionedEntity
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface OptimisticlyLocked { }
