package br.com.dfdevforge.sisfintransaction.transaction.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.dfdevforge.sisfintransaction.commons.entities.ResourceDataEntity;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.transaction.model.account.entities.AccountEntity;
import br.com.dfdevforge.sisfintransaction.transaction.model.account.services.AccountAccessEditionService;
import br.com.dfdevforge.sisfintransaction.transaction.model.account.services.AccountAccessModuleService;
import br.com.dfdevforge.sisfintransaction.transaction.model.account.services.AccountAccessRegistrationService;
import br.com.dfdevforge.sisfintransaction.transaction.model.account.services.AccountExecuteEditionService;
import br.com.dfdevforge.sisfintransaction.transaction.model.account.services.AccountExecuteExclusionService;
import br.com.dfdevforge.sisfintransaction.transaction.model.account.services.AccountExecuteRegistrationService;
import br.com.dfdevforge.sisfintransaction.transaction.model.account.services.AccountExecuteSearchService;

@RestController
@RequestMapping(value = "/account")
public class AccountResource {
	private ResourceDataEntity resourceData = new ResourceDataEntity();

	private final AccountAccessModuleService accountAccessModuleService;
	private final AccountAccessEditionService accountAccessEditionService;
	private final AccountAccessRegistrationService accountAccessRegistrationService;
	private final AccountExecuteSearchService accountExecuteSearchService;
	private final AccountExecuteEditionService accountExecuteEditionService;
	private final AccountExecuteExclusionService accountExecuteExclusionService;
	private final AccountExecuteRegistrationService accountExecuteRegistrationService;

	@Autowired
	public AccountResource(AccountAccessModuleService accountAccessModuleService, AccountAccessEditionService accountAccessEditionService, AccountAccessRegistrationService accountAccessRegistrationService, AccountExecuteSearchService accountExecuteSearchService, AccountExecuteEditionService accountExecuteEditionService, AccountExecuteExclusionService accountExecuteExclusionService, AccountExecuteRegistrationService accountExecuteRegistrationService) {
		this.accountAccessModuleService = accountAccessModuleService;
		this.accountAccessEditionService = accountAccessEditionService;
		this.accountAccessRegistrationService = accountAccessRegistrationService;
		this.accountExecuteSearchService = accountExecuteSearchService;
		this.accountExecuteEditionService = accountExecuteEditionService;
		this.accountExecuteExclusionService = accountExecuteExclusionService;
		this.accountExecuteRegistrationService = accountExecuteRegistrationService;
	}

	@PostMapping(value = "/accessModule")
	public ResponseEntity<ResourceDataEntity> accessModule(@RequestBody AccountEntity account, @RequestParam String token) throws BaseException {
		this.accountAccessModuleService.setParams(account, token);
		this.resourceData.setMap(this.accountAccessModuleService.execute());

		return ResponseEntity.ok(this.resourceData);
	}

	@PostMapping(value = "/accessEdition")
	public ResponseEntity<ResourceDataEntity> accessEdition(@RequestBody AccountEntity account, @RequestParam String token) throws BaseException {
		this.accountAccessEditionService.setParams(account, token);
		this.resourceData.setMap(this.accountAccessEditionService.execute());

		return ResponseEntity.ok(this.resourceData);
	}

	@PostMapping(value = "/accessRegistration")
	public ResponseEntity<ResourceDataEntity> accessRegistration(@RequestBody AccountEntity account, @RequestParam String token) throws BaseException {
		this.accountAccessRegistrationService.setParams(account, token);
		this.resourceData.setMap(this.accountAccessRegistrationService.execute());

		return ResponseEntity.ok(this.resourceData);
	}

	@PostMapping(value = "/executeSearch")
	public ResponseEntity<ResourceDataEntity> executeSearch(@RequestBody AccountEntity account, @RequestParam String token) throws BaseException {
		this.accountExecuteSearchService.setParams(account, token);
		this.resourceData.setMap(this.accountExecuteSearchService.execute());

		return ResponseEntity.ok(this.resourceData);
	}

	@PostMapping(value = "/executeEdition")
	public ResponseEntity<ResourceDataEntity> executeEdition(@RequestBody AccountEntity account, @RequestParam String token) throws BaseException {
		this.accountExecuteEditionService.setParams(account, token);
		this.resourceData.setMap(this.accountExecuteEditionService.execute());

		return ResponseEntity.ok(this.resourceData);
	}

	@PostMapping(value = "/executeExclusion")
	public ResponseEntity<ResourceDataEntity> executeExclusion(@RequestBody AccountEntity account, @RequestParam String token) throws BaseException {
		this.accountExecuteExclusionService.setParams(account, token);
		this.resourceData.setMap(this.accountExecuteExclusionService.execute());

		return ResponseEntity.ok(this.resourceData);
	}

	@PostMapping(value = "/executeRegistration")
	public ResponseEntity<ResourceDataEntity> executeRegistration(@RequestBody AccountEntity account, @RequestParam String token) throws BaseException {
		this.accountExecuteRegistrationService.setParams(account, token);
		this.resourceData.setMap(this.accountExecuteRegistrationService.execute());

		return ResponseEntity.ok(this.resourceData);
	}
}