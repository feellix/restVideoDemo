package io.feellix.restvideodemo.config;

import io.feellix.restvideodemo.file.FileStorageRepository;
import io.feellix.restvideodemo.file.DevNullFileStorageRepository;
import org.apache.tika.Tika;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VideoDemoConfig {

    @Bean
    public Mapper mapper() {
        return new DozerBeanMapper();
    }

    @Bean
    public FileStorageRepository storageRepository() {
        return new DevNullFileStorageRepository();
    }

    @Bean
    public Tika defaultDetector() {
        return new Tika();
    }
}
