package test.comparusua.config;

import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import test.comparusua.convertors.EntityRestConverter;

@Configuration
public class MapStructConfig {

    @Bean
    public EntityRestConverter entityRestMapper() {
        return Mappers.getMapper(EntityRestConverter.class);
    }
}
