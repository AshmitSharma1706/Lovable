package com.project.lovable.config;

import io.minio.MinioClient;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "minio")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Data
public class StorageConfig {

    String url;
    String accessKey;
    String secretKey;

    @Bean
    public MinioClient minioClient(){
        return MinioClient.builder()
                .endpoint(url)
                .credentials(accessKey,secretKey)
                .build();
    }
}
