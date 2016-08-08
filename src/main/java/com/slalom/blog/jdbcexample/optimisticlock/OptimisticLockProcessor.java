package com.slalom.blog.jdbcexample.optimisticlock;
 
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;
import org.springframework.aop.framework.ProxyFactory;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import java.lang.reflect.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Processor implements optimistic locking on methods that are tagged with
 * the &amp;OptimisticlyLocked annotation.
 * The first argument of the method must be the Entity being saved.
 * The Entity must implement the com.slalom.blog.jdbcexample.VersionedEntity interface.
 */
@Component
public class OptimisticLockProcessor implements BeanPostProcessor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ApplicationContext appContext;

    public Object postProcessBeforeInitialization(final Object bean, String beanName) throws BeansException {

        Method[] methods = bean.getClass().getDeclaredMethods();
        ProxyFactory pf = new ProxyFactory(bean);

        for (Method method : methods) {
            
            // Check for our optimistic lock annotation
            if (method.isAnnotationPresent(OptimisticlyLocked.class)) {

                // This proxy intercepts method calls and does the version check
                pf.addAdvice(new MethodInterceptor() {
                    public Object invoke(MethodInvocation mi) throws Throwable {
                        
                        // Only versioned Entities are acceptable
                        if (mi.getArguments()[0] instanceof VersionedEntity) {
                            VersionedEntity inputDataObject = (VersionedEntity) mi.getArguments()[0];
                            
                            // It was necessary to get the jdbcTemplate here in the handler
                            // It is not possible to autowire this bean in at startup.
                            JdbcTemplate jdbcTemplate = (JdbcTemplate) appContext.getBean("jdbcTemplate");

                            Long databaseVersion = jdbcTemplate.queryForObject(
                                "select version from " + inputDataObject.getTableName() + " where id = ?",
                                Long.class, inputDataObject.getId());
                            
                            // If the versions match, we're good to save
                            // Bump up the version
                            if (inputDataObject.getVersion().equals(databaseVersion)) {
                                Long newVersion = inputDataObject.getVersion() + 1L;
                                inputDataObject.setVersion(newVersion);
                            }
                            else {
                                // Here the versions dont match, indicating someone else has saved before us.
                                throw new OptimisticLockException("Expected version=" + databaseVersion + ", but got version=" + inputDataObject.getVersion() + ".  Refresh your data.");
                            }
                        }
                        
                        // Call the method that was originally intended
                        return mi.proceed();
                    }
                });
                
                // Return the bean that has been wrapped with our proxy object
                return pf.getProxy();
            }
        }

        return bean;
    } 

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
