package attache.devs.popote.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file")
public record FileParams(
        String customerDir,
        String baseUrl
){}

