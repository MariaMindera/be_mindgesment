package com.mindera.school.mindgesment.configurations;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OrikaConfiguration class exists to configure MapperFacade class.
 * <p>
 * This project uses mapper to convert entities into similar objects to respond to requests and the opposite.
 */
@Configuration
public class OrikaConfiguration {

    @Bean
    public MapperFacade configureOrika() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().mapNulls(false).build();
        return mapperFactory.getMapperFacade();
    }
}
