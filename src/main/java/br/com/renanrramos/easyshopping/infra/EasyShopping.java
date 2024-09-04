/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 24/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.infra;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
@Slf4j
public class EasyShopping {
	public static void main(String[] args) {
		final Dotenv dotenv = Dotenv.configure().load();
		dotenv.entries()
				.stream().filter(dotenvEntry -> dotenvEntry.getKey().equals("REALM_KEY"))
						.forEach(dotenvEntry ->
						log.info("Key: {} | Value: {}", dotenvEntry.getKey(), dotenvEntry.getValue()));
		SpringApplication.run(EasyShopping.class, args);
	}
}
