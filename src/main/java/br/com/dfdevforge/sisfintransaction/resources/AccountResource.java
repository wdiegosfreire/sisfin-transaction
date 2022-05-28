package br.com.dfdevforge.sisfintransaction.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.dfdevforge.common.entities.ResourceDataEntity;
import br.com.dfdevforge.common.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.entities.AccountEntity;
import br.com.dfdevforge.sisfintransaction.services.account.AccountAccessEditionService;
import br.com.dfdevforge.sisfintransaction.services.account.AccountAccessModuleService;
import br.com.dfdevforge.sisfintransaction.services.account.AccountAccessRegistrationService;
import br.com.dfdevforge.sisfintransaction.services.account.AccountExecuteEditionService;
import br.com.dfdevforge.sisfintransaction.services.account.AccountExecuteExclusionService;
import br.com.dfdevforge.sisfintransaction.services.account.AccountExecuteRegistrationService;
import br.com.dfdevforge.sisfintransaction.services.account.AccountExecuteSearchService;

@RestController
@RequestMapping(value = "/account")
public class AccountResource {
	private ResourceDataEntity resourceData = new ResourceDataEntity();

	@Autowired private AccountAccessModuleService accountAccessModuleService;
	@Autowired private AccountAccessEditionService accountAccessEditionService;
	@Autowired private AccountAccessRegistrationService accountAccessRegistrationService;

	@Autowired private AccountExecuteSearchService accountExecuteSearchService;
	@Autowired private AccountExecuteEditionService accountExecuteEditionService;
	@Autowired private AccountExecuteExclusionService accountExecuteExclusionService;
	@Autowired private AccountExecuteRegistrationService accountExecuteRegistrationService;

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