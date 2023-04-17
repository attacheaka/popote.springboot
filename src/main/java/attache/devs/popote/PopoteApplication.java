package attache.devs.popote;

import attache.devs.popote.utils.FileParams;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties({FileParams.class})
@SpringBootApplication
public class PopoteApplication {

    public static void main(String[] args) {
        SpringApplication.run(PopoteApplication.class, args);
    }

}
