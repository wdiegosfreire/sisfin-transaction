package br.com.dfdevforge.sisfintransaction.supermarket.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dfdevforge.sisfintransaction.commons.entities.ResourceDataEntity;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.supermarket.model.company.entities.CompanyEntity;
import br.com.dfdevforge.sisfintransaction.supermarket.model.company.services.CompanyAccessEditionService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.company.services.CompanyAccessModuleService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.company.services.CompanyExecuteEditionService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.company.services.CompanyExecuteExclusionService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.company.services.CompanyExecuteRegistrationService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/company")
public class CompanyResource {
	private final CompanyAccessModuleService companyAccessModuleService;
	private final CompanyAccessEditionService companyAccessEditionService;
	private final CompanyExecuteEditionService companyExecuteEditionService;
	private final CompanyExecuteExclusionService companyExecuteExclusionService;
	private final CompanyExecuteRegistrationService companyExecuteRegistrationService;

	@PostMapping(value = "/accessModule")
	public ResponseEntity<ResourceDataEntity> accessModule(@RequestBody CompanyEntity company) throws BaseException {
		this.companyAccessModuleService.setParams(company);

		ResourceDataEntity resourceData = new ResourceDataEntity();
		resourceData.setMap(this.companyAccessModuleService.execute());

		return ResponseEntity.ok(resourceData);
	}

	@PostMapping(value = "/accessEdition")
	public ResponseEntity<ResourceDataEntity> accessEdition(@RequestBody CompanyEntity company) throws BaseException {
		this.companyAccessEditionService.setParams(company);

		ResourceDataEntity resourceData = new ResourceDataEntity();
		resourceData.setMap(this.companyAccessEditionService.execute());

		return ResponseEntity.ok(resourceData);
	}

	@PostMapping(value = "/executeEdition")
	public ResponseEntity<ResourceDataEntity> executeEdition(@RequestBody CompanyEntity company) throws BaseException {
		this.companyExecuteEditionService.setParams(company);

		ResourceDataEntity resourceData = new ResourceDataEntity();
		resourceData.setMap(this.companyExecuteEditionService.execute());

		return ResponseEntity.ok(resourceData);
	}

	@PostMapping(value = "/executeExclusion")
	public ResponseEntity<ResourceDataEntity> executeExclusion(@RequestBody CompanyEntity company) throws BaseException {
		this.companyExecuteExclusionService.setParams(company);

		ResourceDataEntity resourceData = new ResourceDataEntity();
		resourceData.setMap(this.companyExecuteExclusionService.execute());

		return ResponseEntity.ok(resourceData);
	}

	@PostMapping(value = "/executeRegistration")
	public ResponseEntity<ResourceDataEntity> executeRegistration(@RequestBody CompanyEntity company) throws BaseException {
		this.companyExecuteRegistrationService.setParams(company);

		ResourceDataEntity resourceData = new ResourceDataEntity();
		resourceData.setMap(this.companyExecuteRegistrationService.execute());

		return ResponseEntity.ok(resourceData);
	}
}