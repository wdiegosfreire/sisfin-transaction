package br.com.dfdevforge.sisfintransaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class SisfinTransactionApplication {
	public static void main(String[] args) {
		SpringApplication.run(SisfinTransactionApplication.class, args);
	}
}