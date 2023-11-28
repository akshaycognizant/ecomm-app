package com.ecommerce.ecommerce.config;

import com.ecommerce.ecommerce.entity.*;
import jakarta.persistence.EntityManager;

import jakarta.persistence.metamodel.EntityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {
    private EntityManager entityManager;

    @Autowired
    MyDataRestConfig(EntityManager theEntityManager){
        entityManager = theEntityManager;
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        RepositoryRestConfigurer.super.configureRepositoryRestConfiguration(config, cors);
        HttpMethod[] unsupportedActions = {HttpMethod.DELETE,HttpMethod.PUT,HttpMethod.POST,HttpMethod.PATCH};
        //disable http methods which we don't need (for product)
        disableHTTPMethods(Product.class,config,unsupportedActions);
        //disable http methods which we don't need (for product-category)
        disableHTTPMethods(ProductCategory.class,config,unsupportedActions);
        //disable http methods which we don't need (for country)
        disableHTTPMethods(Country.class,config,unsupportedActions);
        //disable http methods which we don't need (for state)
        disableHTTPMethods(State.class,config,unsupportedActions);
        //disable http methods which we don't need (for order)
        disableHTTPMethods(Order.class,config,unsupportedActions);
        //method to expose the ids
        exposeIds(config);

        //configure cors mapping
        cors.addMapping("/api/**").allowedOrigins("http://localhost:4200");
    }
    public void disableHTTPMethods(Class className,RepositoryRestConfiguration config,HttpMethod[] unsupportedActions){
        config.getExposureConfiguration().forDomainType(className)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(unsupportedActions))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(unsupportedActions));
    }
    
    private void exposeIds(RepositoryRestConfiguration config){
        //expose entity ids

        //get list of all entity classes from entity manager
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();

        //create an array of entity types
        List<Class> entityClasses = new ArrayList<>();

        //get the entity types
        for(EntityType tempEntity :entities){
            entityClasses.add(tempEntity.getJavaType());
        }
        //expose the ids for the arrayof entity/domain types
        Class[] domainTypes = entityClasses.toArray(new Class[0]);
        config.exposeIdsFor(domainTypes);
    }
}
