package ro.mpp2024.rest;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.mpp2024.persistence.repository.UtilizatorRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
public class RepositoryConfig {

    @Bean
    public UtilizatorRepository utilizatorRepository() {
        Properties props = new Properties();

        try (InputStream in = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            props.load(in);
        } catch (IOException e) {
            throw new RuntimeException("Nu pot incarca application.properties", e);
        }

        return new UtilizatorRepository(props);
    }
}