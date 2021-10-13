package soft.secure_disk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAsync
@SpringBootApplication
@ConfigurationPropertiesScan
@EnableTransactionManagement
public class SecureDiskApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecureDiskApplication.class, args);
    }

}
