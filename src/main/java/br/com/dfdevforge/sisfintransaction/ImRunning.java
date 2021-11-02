package br.com.dfdevforge.sisfintransaction;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class ImRunning {
	@GetMapping
	public String imRunning() {
		return "Sisfin Transaction is running";
	}
}
