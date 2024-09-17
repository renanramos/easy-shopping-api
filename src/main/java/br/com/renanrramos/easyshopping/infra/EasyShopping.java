/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 24/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.infra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "br.com.renanrramos.easyshopping.model")
@EnableJpaRepositories(basePackages = "br.com.renanrramos.easyshopping.interfaceadapter.repository")
public class EasyShopping {
	public static void main(String[] args) {
		SpringApplication.run(EasyShopping.class, args);
	}
}
