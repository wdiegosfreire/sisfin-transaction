package br.com.dfdevforge.sisfintransaction.supermarket.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dfdevforge.sisfintransaction.commons.entities.ResourceDataEntity;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.supermarket.model.productcategory.entities.ProductCategoryEntity;
import br.com.dfdevforge.sisfintransaction.supermarket.model.productcategory.services.ProductCategoryAccessEditionService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.productcategory.services.ProductCategoryAccessModuleService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.productcategory.services.ProductCategoryExecuteEditionService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.productcategory.services.ProductCategoryExecuteExclusionService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.productcategory.services.ProductCategoryExecuteRegistrationService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/productCategory")
public class ProductCategoryResource {
	private final ProductCategoryAccessModuleService productCategoryAccessModuleService;
	private final ProductCategoryAccessEditionService productCategoryAccessEditionService;
	private final ProductCategoryExecuteEditionService productCategoryExecuteEditionService;
	private final ProductCategoryExecuteExclusionService productCategoryExecuteExclusionService;
	private final ProductCategoryExecuteRegistrationService productCategoryExecuteRegistrationService;

	@PostMapping(value = "/accessModule")
	public ResponseEntity<ResourceDataEntity> accessModule(@RequestBody ProductCategoryEntity productCategory) throws BaseException {
		this.productCategoryAccessModuleService.setParams(productCategory);

		ResourceDataEntity resourceData = new ResourceDataEntity();
		resourceData.setMap(this.productCategoryAccessModuleService.execute());

		return ResponseEntity.ok(resourceData);
	}

	@PostMapping(value = "/accessEdition")
	public ResponseEntity<ResourceDataEntity> accessEdition(@RequestBody ProductCategoryEntity productCategory) throws BaseException {
		this.productCategoryAccessEditionService.setParams(productCategory);

		ResourceDataEntity resourceData = new ResourceDataEntity();
		resourceData.setMap(this.productCategoryAccessEditionService.execute());

		return ResponseEntity.ok(resourceData);
	}

	@PostMapping(value = "/executeEdition")
	public ResponseEntity<ResourceDataEntity> executeEdition(@RequestBody ProductCategoryEntity productCategory) throws BaseException {
		this.productCategoryExecuteEditionService.setParams(productCategory);

		ResourceDataEntity resourceData = new ResourceDataEntity();
		resourceData.setMap(this.productCategoryExecuteEditionService.execute());

		return ResponseEntity.ok(resourceData);
	}

	@PostMapping(value = "/executeExclusion")
	public ResponseEntity<ResourceDataEntity> executeExclusion(@RequestBody ProductCategoryEntity productCategory) throws BaseException {
		this.productCategoryExecuteExclusionService.setParams(productCategory);

		ResourceDataEntity resourceData = new ResourceDataEntity();
		resourceData.setMap(this.productCategoryExecuteExclusionService.execute());

		return ResponseEntity.ok(resourceData);
	}

	@PostMapping(value = "/executeRegistration")
	public ResponseEntity<ResourceDataEntity> executeRegistration(@RequestBody ProductCategoryEntity productCategory) throws BaseException {
		this.productCategoryExecuteRegistrationService.setParams(productCategory);

		ResourceDataEntity resourceData = new ResourceDataEntity();
		resourceData.setMap(this.productCategoryExecuteRegistrationService.execute());

		return ResponseEntity.ok(resourceData);
	}
}
