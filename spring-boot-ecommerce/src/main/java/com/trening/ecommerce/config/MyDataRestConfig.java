package com.trening.ecommerce.config;

import com.trening.ecommerce.dao.CountryRepository;
import com.trening.ecommerce.dao.StateRepository;
import com.trening.ecommerce.entity.Order;
import com.trening.ecommerce.entity.Product;
import com.trening.ecommerce.entity.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

    @Value("${allowed.origins}")
    private String[] allowedOrigins;

    private EntityManager entityManager;

    @Autowired
    public MyDataRestConfig(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry corsRegistry) {

        HttpMethod[] theUnsupportedActions = {HttpMethod.PUT, HttpMethod.POST,
                                              HttpMethod.DELETE, HttpMethod.PATCH};

        // configure cors mapping
        corsRegistry.addMapping(config.getBasePath() + "/**").allowedOrigins(allowedOrigins);

        //disable methods PUT, POST, DELETE
        disableHttpMethods(Product.class, config, theUnsupportedActions);
        disableHttpMethods(ProductCategory.class, config, theUnsupportedActions);
        disableHttpMethods(CountryRepository.class, config, theUnsupportedActions);
        disableHttpMethods(StateRepository.class, config, theUnsupportedActions);
        disableHttpMethods(Order.class, config, theUnsupportedActions);

        //expose ids for category
        exposeIds(config);
    }

    private void disableHttpMethods(Class theClass, RepositoryRestConfiguration config, HttpMethod[] theUnsupportedActions) {
        config.getExposureConfiguration()
                .forDomainType(theClass)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions));
    }

    private void exposeIds(RepositoryRestConfiguration config) {
        //expose entity id
        // get a list of all entity class from the entity manager
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();

        //create an array od the entity types
        List<Class> entityClasses = new ArrayList<>();

        //get the entity types for the entities
        for(EntityType tempEntityType : entities) {
            entityClasses.add(tempEntityType.getJavaType());
        }

        //expose the entity ids for the array of entity/domain types
        Class[] domainTypes = entityClasses.toArray(new Class[0]);
        config.exposeIdsFor(domainTypes);
    }


}
