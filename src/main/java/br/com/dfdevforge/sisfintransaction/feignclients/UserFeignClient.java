package br.com.dfdevforge.sisfintransaction.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.dfdevforge.common.entities.ResourceDataEntity;

@Component
@FeignClient(name="sisfin-maintenance", url="${SISFIN_URL_MAINTENANCE}", path="/userfeignserver")
public interface UserFeignClient {
	@GetMapping(value = "/{identity}")
	public ResponseEntity<ResourceDataEntity> findByIdentity(@PathVariable long identity);
}