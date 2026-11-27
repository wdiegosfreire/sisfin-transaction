package br.com.dfdevforge.sisfintransaction.supermarket.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dfdevforge.sisfintransaction.commons.entities.ResourceDataEntity;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.supermarket.model.productcompany.entities.ProductCompanyEntity;
import br.com.dfdevforge.sisfintransaction.supermarket.model.productcompany.services.ProductCompanyAccessEditionService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.productcompany.services.ProductCompanyAccessModuleService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.productcompany.services.ProductCompanyExecuteEditionService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.productcompany.services.ProductCompanyExecuteExclusionService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.productcompany.services.ProductCompanyExecuteRegistrationService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/productCompany")
public class ProductCompanyResource {
	private final ProductCompanyAccessModuleService productCompanyAccessModuleService;
	private final ProductCompanyAccessEditionService productCompanyAccessEditionService;
	private final ProductCompanyExecuteEditionService productCompanyExecuteEditionService;
	private final ProductCompanyExecuteExclusionService productCompanyExecuteExclusionService;
	private final ProductCompanyExecuteRegistrationService productCompanyExecuteRegistrationService;

	@PostMapping(value = "/accessModule")
	public ResponseEntity<ResourceDataEntity> accessModule(@RequestBody ProductCompanyEntity productCompany) throws BaseException {
		this.productCompanyAccessModuleService.setParams(productCompany);

		ResourceDataEntity resourceData = new ResourceDataEntity();
		resourceData.setMap(this.productCompanyAccessModuleService.execute());

		return ResponseEntity.ok(resourceData);
	}

	@PostMapping(value = "/accessEdition")
	public ResponseEntity<ResourceDataEntity> accessEdition(@RequestBody ProductCompanyEntity productCompany) throws BaseException {
		this.productCompanyAccessEditionService.setParams(productCompany);

		ResourceDataEntity resourceData = new ResourceDataEntity();
		resourceData.setMap(this.productCompanyAccessEditionService.execute());

		return ResponseEntity.ok(resourceData);
	}

	@PostMapping(value = "/executeEdition")
	public ResponseEntity<ResourceDataEntity> executeEdition(@RequestBody ProductCompanyEntity productCompany) throws BaseException {
		this.productCompanyExecuteEditionService.setParams(productCompany);

		ResourceDataEntity resourceData = new ResourceDataEntity();
		resourceData.setMap(this.productCompanyExecuteEditionService.execute());

		return ResponseEntity.ok(resourceData);
	}

	@PostMapping(value = "/executeExclusion")
	public ResponseEntity<ResourceDataEntity> executeExclusion(@RequestBody ProductCompanyEntity productCompany) throws BaseException {
		this.productCompanyExecuteExclusionService.setParams(productCompany);

		ResourceDataEntity resourceData = new ResourceDataEntity();
		resourceData.setMap(this.productCompanyExecuteExclusionService.execute());

		return ResponseEntity.ok(resourceData);
	}

	@PostMapping(value = "/executeRegistration")
	public ResponseEntity<ResourceDataEntity> executeRegistration(@RequestBody ProductCompanyEntity productCompany) throws BaseException {
		this.productCompanyExecuteRegistrationService.setParams(productCompany);

		ResourceDataEntity resourceData = new ResourceDataEntity();
		resourceData.setMap(this.productCompanyExecuteRegistrationService.execute());

		return ResponseEntity.ok(resourceData);
	}
}
