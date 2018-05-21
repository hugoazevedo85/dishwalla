package org.dishwalla.configs;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan(basePackages= {"org.dishwalla.models"})
public class JPAConfig {

}
