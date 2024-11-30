package br.com.dfdevforge.sisfintransaction.statement.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	private ResourceDataEntity resourceData = new ResourceDataEntity();

	@Autowired private BankAccessModuleService bankAccessModuleService;
	@Autowired private BankAccessEditionService bankAccessEditionService;
	@Autowired private BankExecuteSearchService bankExecuteSearchService;
	@Autowired private BankExecuteEditionService bankExecuteEditionService;
	@Autowired private BankExecuteExclusionService bankExecuteExclusionService;
	@Autowired private BankExecuteRegistrationService bankExecuteRegistrationService;

	@PostMapping(value = "/accessModule")
	public ResponseEntity<ResourceDataEntity> accessModule(@RequestBody BankEntity bank, @RequestParam String token) throws BaseException {
		this.bankAccessModuleService.setParams(bank, token);
		this.resourceData.setMap(this.bankAccessModuleService.execute());

		return ResponseEntity.ok(this.resourceData);
	}

	@PostMapping(value = "/accessEdition")
	public ResponseEntity<ResourceDataEntity> accessEdition(@RequestBody BankEntity bank, @RequestParam String token) throws BaseException {
		this.bankAccessEditionService.setParams(bank, token);
		this.resourceData.setMap(this.bankAccessEditionService.execute());

		return ResponseEntity.ok(this.resourceData);
	}

	@PostMapping(value = "/executeSearch")
	public ResponseEntity<ResourceDataEntity> executeSearch(@RequestBody BankEntity bank, @RequestParam String token) throws BaseException {
		this.bankExecuteSearchService.setParams(bank, token);
		this.resourceData.setMap(this.bankExecuteSearchService.execute());

		return ResponseEntity.ok(this.resourceData);
	}

	@PostMapping(value = "/executeEdition")
	public ResponseEntity<ResourceDataEntity> executeEdition(@RequestBody BankEntity bank, @RequestParam String token) throws BaseException {
		this.bankExecuteEditionService.setParams(bank, token);
		this.resourceData.setMap(this.bankExecuteEditionService.execute());

		return ResponseEntity.ok(this.resourceData);
	}

	@PostMapping(value = "/executeExclusion")
	public ResponseEntity<ResourceDataEntity> executeExclusion(@RequestBody BankEntity bank, @RequestParam String token) throws BaseException {
		this.bankExecuteExclusionService.setParams(bank, token);
		this.resourceData.setMap(this.bankExecuteExclusionService.execute());

		return ResponseEntity.ok(this.resourceData);
	}

	@PostMapping(value = "/executeRegistration")
	public ResponseEntity<ResourceDataEntity> executeRegistration(@RequestBody BankEntity bank, @RequestParam String token) throws BaseException {
		this.bankExecuteRegistrationService.setParams(bank, token);
		this.resourceData.setMap(this.bankExecuteRegistrationService.execute());

		return ResponseEntity.ok(this.resourceData);
	}
}