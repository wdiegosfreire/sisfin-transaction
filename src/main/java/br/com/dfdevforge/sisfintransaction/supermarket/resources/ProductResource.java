package br.com.dfdevforge.sisfintransaction.supermarket.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dfdevforge.sisfintransaction.commons.entities.ResourceDataEntity;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.supermarket.model.product.entities.ProductEntity;
import br.com.dfdevforge.sisfintransaction.supermarket.model.product.services.ProductAccessEditionService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.product.services.ProductAccessModuleService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.product.services.ProductAccessRegistrationService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.product.services.ProductExecuteEditionService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.product.services.ProductExecuteExclusionService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.product.services.ProductExecuteRegistrationService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/product")
public class ProductResource {
	private final ProductAccessModuleService productAccessModuleService;
	private final ProductAccessEditionService productAccessEditionService;
	private final ProductAccessRegistrationService productAccessRegistrationService;
	private final ProductExecuteEditionService productExecuteEditionService;
	private final ProductExecuteExclusionService productExecuteExclusionService;
	private final ProductExecuteRegistrationService productExecuteRegistrationService;

	@PostMapping(value = "/accessModule")
	public ResponseEntity<ResourceDataEntity> accessModule(@RequestBody ProductEntity product) throws BaseException {
		this.productAccessModuleService.setParams(product);

		ResourceDataEntity resourceData = new ResourceDataEntity();
		resourceData.setMap(this.productAccessModuleService.execute());

		return ResponseEntity.ok(resourceData);
	}

	@PostMapping(value = "/accessEdition")
	public ResponseEntity<ResourceDataEntity> accessEdition(@RequestBody ProductEntity product) throws BaseException {
		this.productAccessEditionService.setParams(product);

		ResourceDataEntity resourceData = new ResourceDataEntity();
		resourceData.setMap(this.productAccessEditionService.execute());

		return ResponseEntity.ok(resourceData);
	}

	@PostMapping(value = "/accessRegistration")
	public ResponseEntity<ResourceDataEntity> accessRegistration(@RequestBody ProductEntity product) throws BaseException {
		this.productAccessRegistrationService.setParams(product);

		ResourceDataEntity resourceData = new ResourceDataEntity();
		resourceData.setMap(this.productAccessRegistrationService.execute());

		return ResponseEntity.ok(resourceData);
	}

	@PostMapping(value = "/executeEdition")
	public ResponseEntity<ResourceDataEntity> executeEdition(@RequestBody ProductEntity product) throws BaseException {
		this.productExecuteEditionService.setParams(product);

		ResourceDataEntity resourceData = new ResourceDataEntity();
		resourceData.setMap(this.productExecuteEditionService.execute());

		return ResponseEntity.ok(resourceData);
	}

	@PostMapping(value = "/executeExclusion")
	public ResponseEntity<ResourceDataEntity> executeExclusion(@RequestBody ProductEntity product) throws BaseException {
		this.productExecuteExclusionService.setParams(product);

		ResourceDataEntity resourceData = new ResourceDataEntity();
		resourceData.setMap(this.productExecuteExclusionService.execute());

		return ResponseEntity.ok(resourceData);
	}

	@PostMapping(value = "/executeRegistration")
	public ResponseEntity<ResourceDataEntity> executeRegistration(@RequestBody ProductEntity product) throws BaseException {
		this.productExecuteRegistrationService.setParams(product);

		ResourceDataEntity resourceData = new ResourceDataEntity();
		resourceData.setMap(this.productExecuteRegistrationService.execute());

		return ResponseEntity.ok(resourceData);
	}
}