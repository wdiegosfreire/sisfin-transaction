package br.com.dfdevforge.sisfintransaction.supermarket.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dfdevforge.sisfintransaction.commons.entities.ResourceDataEntity;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.supermarket.model.productproperty.entities.ProductPropertyEntity;
import br.com.dfdevforge.sisfintransaction.supermarket.model.productproperty.services.ProductPropertyAccessEditionService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.productproperty.services.ProductPropertyAccessModuleService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.productproperty.services.ProductPropertyExecuteEditionService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.productproperty.services.ProductPropertyExecuteExclusionService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.productproperty.services.ProductPropertyExecuteRegistrationService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/productProperty")
public class ProductPropertyResource {
	private final ProductPropertyAccessModuleService productPropertyAccessModuleService;
	private final ProductPropertyAccessEditionService productPropertyAccessEditionService;
	private final ProductPropertyExecuteEditionService productPropertyExecuteEditionService;
	private final ProductPropertyExecuteExclusionService productPropertyExecuteExclusionService;
	private final ProductPropertyExecuteRegistrationService productPropertyExecuteRegistrationService;

	@PostMapping(value = "/accessModule")
	public ResponseEntity<ResourceDataEntity> accessModule(@RequestBody ProductPropertyEntity productProperty) throws BaseException {
		this.productPropertyAccessModuleService.setParams(productProperty);

		ResourceDataEntity resourceData = new ResourceDataEntity();
		resourceData.setMap(this.productPropertyAccessModuleService.execute());

		return ResponseEntity.ok(resourceData);
	}

	@PostMapping(value = "/accessEdition")
	public ResponseEntity<ResourceDataEntity> accessEdition(@RequestBody ProductPropertyEntity productProperty) throws BaseException {
		this.productPropertyAccessEditionService.setParams(productProperty);

		ResourceDataEntity resourceData = new ResourceDataEntity();
		resourceData.setMap(this.productPropertyAccessEditionService.execute());

		return ResponseEntity.ok(resourceData);
	}

	@PostMapping(value = "/executeEdition")
	public ResponseEntity<ResourceDataEntity> executeEdition(@RequestBody ProductPropertyEntity productProperty) throws BaseException {
		this.productPropertyExecuteEditionService.setParams(productProperty);

		ResourceDataEntity resourceData = new ResourceDataEntity();
		resourceData.setMap(this.productPropertyExecuteEditionService.execute());

		return ResponseEntity.ok(resourceData);
	}

	@PostMapping(value = "/executeExclusion")
	public ResponseEntity<ResourceDataEntity> executeExclusion(@RequestBody ProductPropertyEntity productProperty) throws BaseException {
		this.productPropertyExecuteExclusionService.setParams(productProperty);

		ResourceDataEntity resourceData = new ResourceDataEntity();
		resourceData.setMap(this.productPropertyExecuteExclusionService.execute());

		return ResponseEntity.ok(resourceData);
	}

	@PostMapping(value = "/executeRegistration")
	public ResponseEntity<ResourceDataEntity> executeRegistration(@RequestBody ProductPropertyEntity productProperty) throws BaseException {
		this.productPropertyExecuteRegistrationService.setParams(productProperty);

		ResourceDataEntity resourceData = new ResourceDataEntity();
		resourceData.setMap(this.productPropertyExecuteRegistrationService.execute());

		return ResponseEntity.ok(resourceData);
	}
}
