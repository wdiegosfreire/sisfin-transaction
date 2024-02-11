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
import br.com.dfdevforge.sisfintransaction.statement.entities.StatementEntity;
import br.com.dfdevforge.sisfintransaction.statement.service.statement.StatementAccessEditionService;
import br.com.dfdevforge.sisfintransaction.statement.service.statement.StatementAccessModuleService;
import br.com.dfdevforge.sisfintransaction.statement.service.statement.StatementAccessRegistrationService;
import br.com.dfdevforge.sisfintransaction.statement.service.statement.StatementExecuteEditionService;
import br.com.dfdevforge.sisfintransaction.statement.service.statement.StatementExecuteExclusionService;
import br.com.dfdevforge.sisfintransaction.statement.service.statement.StatementExecuteRegistrationService;
import br.com.dfdevforge.sisfintransaction.statement.service.statement.StatementExecuteSearchService;

@RestController
@RequestMapping(value = "/statement")
public class StatementResource {
	private ResourceDataEntity resourceData = new ResourceDataEntity();

	@Autowired private StatementAccessModuleService statementAccessModuleService;
	@Autowired private StatementAccessEditionService statementAccessEditionService;
	@Autowired private StatementAccessRegistrationService statementAccessRegistrationService;
	
	@Autowired private StatementExecuteSearchService statementExecuteSearchService;
	@Autowired private StatementExecuteEditionService statementExecuteEditionService;
	@Autowired private StatementExecuteExclusionService statementExecuteExclusionService;
	@Autowired private StatementExecuteRegistrationService statementExecuteRegistrationService;

	@PostMapping(value = "/accessModule")
	public ResponseEntity<ResourceDataEntity> accessModule(@RequestBody StatementEntity statement, @RequestParam String token) throws BaseException {
		this.statementAccessModuleService.setParams(statement, token);
		this.resourceData.setMap(this.statementAccessModuleService.execute());

		return ResponseEntity.ok(this.resourceData);
	}

	@PostMapping(value = "/accessEdition")
	public ResponseEntity<ResourceDataEntity> accessEdition(@RequestBody StatementEntity statement, @RequestParam String token) throws BaseException {
		this.statementAccessEditionService.setParams(statement, token);
		this.resourceData.setMap(this.statementAccessEditionService.execute());

		return ResponseEntity.ok(this.resourceData);
	}

	@PostMapping(value = "/accessRegistration")
	public ResponseEntity<ResourceDataEntity> accessRegistration(@RequestBody StatementEntity statement, @RequestParam String token) throws BaseException {
		this.statementAccessRegistrationService.setParams(statement, token);
		this.resourceData.setMap(this.statementAccessRegistrationService.execute());
		
		return ResponseEntity.ok(this.resourceData);
	}

	@PostMapping(value = "/executeSearch")
	public ResponseEntity<ResourceDataEntity> executeSearch(@RequestBody StatementEntity statement, @RequestParam String token) throws BaseException {
		this.statementExecuteSearchService.setParams(statement, token);
		this.resourceData.setMap(this.statementExecuteSearchService.execute());

		return ResponseEntity.ok(this.resourceData);
	}

	@PostMapping(value = "/executeEdition")
	public ResponseEntity<ResourceDataEntity> executeEdition(@RequestBody StatementEntity statement, @RequestParam String token) throws BaseException {
		this.statementExecuteEditionService.setParams(statement, token);
		this.resourceData.setMap(this.statementExecuteEditionService.execute());

		return ResponseEntity.ok(this.resourceData);
	}

	@PostMapping(value = "/executeExclusion")
	public ResponseEntity<ResourceDataEntity> executeExclusion(@RequestBody StatementEntity statement, @RequestParam String token) throws BaseException {
		this.statementExecuteExclusionService.setParams(statement, token);
		this.resourceData.setMap(this.statementExecuteExclusionService.execute());

		return ResponseEntity.ok(this.resourceData);
	}

	@PostMapping(value = "/executeRegistration")
	public ResponseEntity<ResourceDataEntity> executeRegistration(@RequestBody StatementEntity statement, @RequestParam String token) throws BaseException {
		this.statementExecuteRegistrationService.setParams(statement, token);
		this.resourceData.setMap(this.statementExecuteRegistrationService.execute());

		return ResponseEntity.ok(this.resourceData);
	}
}