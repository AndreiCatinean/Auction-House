package proj.auctionhousebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class AuctionHouseBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuctionHouseBackendApplication.class, args);
    }
}
