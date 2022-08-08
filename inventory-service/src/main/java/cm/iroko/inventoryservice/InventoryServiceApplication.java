package cm.iroko.inventoryservice;

import cm.iroko.inventoryservice.model.Inventory;
import cm.iroko.inventoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepository) {

		return args -> {
			Inventory inv1 = new Inventory();
			inv1.setStock(100);
			inv1.setSkuCode("HUWAEI");

			Inventory inv2 = new Inventory();
			inv2.setStock(10);
			inv2.setSkuCode("SAMGSUNG");

			inventoryRepository.save(inv1);
			inventoryRepository.save(inv2);
		};
	}
}
