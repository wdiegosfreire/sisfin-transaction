package br.com.dfdevforge.sisfintransaction.supermarket.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.dfdevforge.sisfintransaction.commons.entities.ResourceDataEntity;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.supermarket.model.brand.entities.BrandEntity;
import br.com.dfdevforge.sisfintransaction.supermarket.model.brand.services.BrandAccessEditionService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.brand.services.BrandAccessModuleService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.brand.services.BrandExecuteEditionService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.brand.services.BrandExecuteExclusionService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.brand.services.BrandExecuteRegistrationService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.brand.services.BrandExecuteSearchService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/brand")
public class BrandResource {
	private ResourceDataEntity resourceData = new ResourceDataEntity();

	private final BrandAccessModuleService brandAccessModuleService;
	private final BrandAccessEditionService brandAccessEditionService;
	private final BrandExecuteSearchService brandExecuteSearchService;
	private final BrandExecuteEditionService brandExecuteEditionService;
	private final BrandExecuteExclusionService brandExecuteExclusionService;
	private final BrandExecuteRegistrationService brandExecuteRegistrationService;

	@PostMapping(value = "/accessModule")
	public ResponseEntity<ResourceDataEntity> accessModule(@RequestBody BrandEntity brand, @RequestParam String token) throws BaseException {
		this.brandAccessModuleService.setParams(brand, token);
		this.resourceData.setMap(this.brandAccessModuleService.execute());

		return ResponseEntity.ok(this.resourceData);
	}

	@PostMapping(value = "/accessEdition")
	public ResponseEntity<ResourceDataEntity> accessEdition(@RequestBody BrandEntity brand, @RequestParam String token) throws BaseException {
		this.brandAccessEditionService.setParams(brand, token);
		this.resourceData.setMap(this.brandAccessEditionService.execute());

		return ResponseEntity.ok(this.resourceData);
	}

	@PostMapping(value = "/executeSearch")
	public ResponseEntity<ResourceDataEntity> executeSearch(@RequestBody BrandEntity brand, @RequestParam String token) throws BaseException {
		this.brandExecuteSearchService.setParams(brand, token);
		this.resourceData.setMap(this.brandExecuteSearchService.execute());

		return ResponseEntity.ok(this.resourceData);
	}

	@PostMapping(value = "/executeEdition")
	public ResponseEntity<ResourceDataEntity> executeEdition(@RequestBody BrandEntity brand, @RequestParam String token) throws BaseException {
		this.brandExecuteEditionService.setParams(brand, token);
		this.resourceData.setMap(this.brandExecuteEditionService.execute());

		return ResponseEntity.ok(this.resourceData);
	}

	@PostMapping(value = "/executeExclusion")
	public ResponseEntity<ResourceDataEntity> executeExclusion(@RequestBody BrandEntity brand, @RequestParam String token) throws BaseException {
		this.brandExecuteExclusionService.setParams(brand, token);
		this.resourceData.setMap(this.brandExecuteExclusionService.execute());

		return ResponseEntity.ok(this.resourceData);
	}

	@PostMapping(value = "/executeRegistration")
	public ResponseEntity<ResourceDataEntity> executeRegistration(@RequestBody BrandEntity brand, @RequestParam String token) throws BaseException {
		this.brandExecuteRegistrationService.setParams(brand, token);
		this.resourceData.setMap(this.brandExecuteRegistrationService.execute());

		return ResponseEntity.ok(this.resourceData);
	}
}