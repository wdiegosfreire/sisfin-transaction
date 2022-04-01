package br.com.dfdevforge.sisfintransaction.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.dfdevforge.sisfintransaction.entities.UserEntity;

@Component
@FeignClient(name="sisfin-maintenance", url="${SISFIN_URL_MAINTENANCE}", path="/userfeignserver")
public interface UserFeignClient {
	@GetMapping(value = "/{token}")
	public UserEntity validateToken(@PathVariable String token);
}