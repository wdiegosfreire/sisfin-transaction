package br.com.dfdevforge.sisfintransaction.supermarket.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dfdevforge.sisfintransaction.commons.entities.ResourceDataEntity;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.supermarket.model.productcompany.dtos.ProductCompanyDtoMapper;
import br.com.dfdevforge.sisfintransaction.supermarket.model.productcompany.dtos.ProductCompanyDtoRequest;
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
	private final ProductCompanyDtoMapper productCompanyDtoMapper;
	private final ProductCompanyAccessModuleService productCompanyAccessModuleService;
	private final ProductCompanyAccessEditionService productCompanyAccessEditionService;
	private final ProductCompanyExecuteEditionService productCompanyExecuteEditionService;
	private final ProductCompanyExecuteExclusionService productCompanyExecuteExclusionService;
	private final ProductCompanyExecuteRegistrationService productCompanyExecuteRegistrationService;

	@PostMapping(value = "/accessModule")
	public ResponseEntity<ResourceDataEntity> accessModule(@RequestBody ProductCompanyDtoRequest productCompany) throws BaseException {
		this.productCompanyAccessModuleService.setParams(this.productCompanyDtoMapper.toEntity(productCompany));

		ResourceDataEntity resourceData = new ResourceDataEntity();
		resourceData.setMap(this.productCompanyDtoMapper.toResponseMap(this.productCompanyAccessModuleService.execute()));

		return ResponseEntity.ok(resourceData);
	}

	@PostMapping(value = "/accessEdition")
	public ResponseEntity<ResourceDataEntity> accessEdition(@RequestBody ProductCompanyDtoRequest productCompany) throws BaseException {
		this.productCompanyAccessEditionService.setParams(this.productCompanyDtoMapper.toEntity(productCompany));

		ResourceDataEntity resourceData = new ResourceDataEntity();
		resourceData.setMap(this.productCompanyDtoMapper.toResponseMap(this.productCompanyAccessEditionService.execute()));

		return ResponseEntity.ok(resourceData);
	}

	@PostMapping(value = "/executeEdition")
	public ResponseEntity<ResourceDataEntity> executeEdition(@RequestBody ProductCompanyDtoRequest productCompany) throws BaseException {
		this.productCompanyExecuteEditionService.setParams(this.productCompanyDtoMapper.toEntity(productCompany));

		ResourceDataEntity resourceData = new ResourceDataEntity();
		resourceData.setMap(this.productCompanyDtoMapper.toResponseMap(this.productCompanyExecuteEditionService.execute()));

		return ResponseEntity.ok(resourceData);
	}

	@PostMapping(value = "/executeExclusion")
	public ResponseEntity<ResourceDataEntity> executeExclusion(@RequestBody ProductCompanyDtoRequest productCompany) throws BaseException {
		this.productCompanyExecuteExclusionService.setParams(this.productCompanyDtoMapper.toEntity(productCompany));

		ResourceDataEntity resourceData = new ResourceDataEntity();
		resourceData.setMap(this.productCompanyDtoMapper.toResponseMap(this.productCompanyExecuteExclusionService.execute()));

		return ResponseEntity.ok(resourceData);
	}

	@PostMapping(value = "/executeRegistration")
	public ResponseEntity<ResourceDataEntity> executeRegistration(@RequestBody ProductCompanyDtoRequest productCompany) throws BaseException {
		this.productCompanyExecuteRegistrationService.setParams(this.productCompanyDtoMapper.toEntity(productCompany));

		ResourceDataEntity resourceData = new ResourceDataEntity();
		resourceData.setMap(this.productCompanyDtoMapper.toResponseMap(this.productCompanyExecuteRegistrationService.execute()));

		return ResponseEntity.ok(resourceData);
	}
}
