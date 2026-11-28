package br.com.dfdevforge.sisfintransaction.statement.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dfdevforge.sisfintransaction.commons.entities.ResourceDataEntity;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.statement.model.bank.entities.BankEntity;
import br.com.dfdevforge.sisfintransaction.statement.model.bank.services.BankAccessEditionService;
import br.com.dfdevforge.sisfintransaction.statement.model.bank.services.BankAccessModuleService;
import br.com.dfdevforge.sisfintransaction.statement.model.bank.services.BankExecuteEditionService;
import br.com.dfdevforge.sisfintransaction.statement.model.bank.services.BankExecuteExclusionService;
import br.com.dfdevforge.sisfintransaction.statement.model.bank.services.BankExecuteRegistrationService;
import br.com.dfdevforge.sisfintransaction.statement.model.bank.services.BankExecuteSearchService;

@RestController
@RequestMapping(value = "/bank")
public class BankResource {
	private final BankAccessModuleService bankAccessModuleService;
	private final BankAccessEditionService bankAccessEditionService;
	private final BankExecuteSearchService bankExecuteSearchService;
	private final BankExecuteEditionService bankExecuteEditionService;
	private final BankExecuteExclusionService bankExecuteExclusionService;
	private final BankExecuteRegistrationService bankExecuteRegistrationService;

	@Autowired
	public BankResource(BankAccessModuleService bankAccessModuleService, BankAccessEditionService bankAccessEditionService, BankExecuteSearchService bankExecuteSearchService, BankExecuteEditionService bankExecuteEditionService, BankExecuteExclusionService bankExecuteExclusionService, BankExecuteRegistrationService bankExecuteRegistrationService) {
		this.bankAccessModuleService = bankAccessModuleService;
		this.bankAccessEditionService = bankAccessEditionService;
		this.bankExecuteSearchService = bankExecuteSearchService;
		this.bankExecuteEditionService = bankExecuteEditionService;
		this.bankExecuteExclusionService = bankExecuteExclusionService;
		this.bankExecuteRegistrationService = bankExecuteRegistrationService;
	}

	@PostMapping(value = "/accessModule")
	public ResponseEntity<ResourceDataEntity> accessModule(@RequestBody BankEntity bank) throws BaseException {
		this.bankAccessModuleService.setParams(bank);

		ResourceDataEntity resourceData = new ResourceDataEntity();
		resourceData.setMap(this.bankAccessModuleService.execute());

		return ResponseEntity.ok(resourceData);
	}

	@PostMapping(value = "/accessEdition")
	public ResponseEntity<ResourceDataEntity> accessEdition(@RequestBody BankEntity bank) throws BaseException {
		this.bankAccessEditionService.setParams(bank);

		ResourceDataEntity resourceData = new ResourceDataEntity();
		resourceData.setMap(this.bankAccessEditionService.execute());

		return ResponseEntity.ok(resourceData);
	}

	@PostMapping(value = "/executeSearch")
	public ResponseEntity<ResourceDataEntity> executeSearch(@RequestBody BankEntity bank) throws BaseException {
		this.bankExecuteSearchService.setParams(bank);

		ResourceDataEntity resourceData = new ResourceDataEntity();
		resourceData.setMap(this.bankExecuteSearchService.execute());

		return ResponseEntity.ok(resourceData);
	}

	@PostMapping(value = "/executeEdition")
	public ResponseEntity<ResourceDataEntity> executeEdition(@RequestBody BankEntity bank) throws BaseException {
		this.bankExecuteEditionService.setParams(bank);

		ResourceDataEntity resourceData = new ResourceDataEntity();
		resourceData.setMap(this.bankExecuteEditionService.execute());

		return ResponseEntity.ok(resourceData);
	}

	@PostMapping(value = "/executeExclusion")
	public ResponseEntity<ResourceDataEntity> executeExclusion(@RequestBody BankEntity bank) throws BaseException {
		this.bankExecuteExclusionService.setParams(bank);

		ResourceDataEntity resourceData = new ResourceDataEntity();
		resourceData.setMap(this.bankExecuteExclusionService.execute());

		return ResponseEntity.ok(resourceData);
	}

	@PostMapping(value = "/executeRegistration")
	public ResponseEntity<ResourceDataEntity> executeRegistration(@RequestBody BankEntity bank) throws BaseException {
		this.bankExecuteRegistrationService.setParams(bank);

		ResourceDataEntity resourceData = new ResourceDataEntity();
		resourceData.setMap(this.bankExecuteRegistrationService.execute());

		return ResponseEntity.ok(resourceData);
	}
}