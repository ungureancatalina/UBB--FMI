package ro.mpp2024.persistence.repository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.mpp2024.persistence.repository.ConfigUtils;
import ro.mpp2024.persistence.repository.CursaRepository;

@Configuration
public class PersistenceConfig {

    @Bean
    public CursaRepository cursaRepository() {
        return new CursaRepository(ConfigUtils.loadProperties());
    }
}
