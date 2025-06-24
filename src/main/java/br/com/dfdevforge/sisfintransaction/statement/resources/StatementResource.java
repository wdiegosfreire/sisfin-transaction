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
import br.com.dfdevforge.sisfintransaction.statement.model.statement.entities.StatementEntity;
import br.com.dfdevforge.sisfintransaction.statement.model.statement.services.StatementAccessEditionService;
import br.com.dfdevforge.sisfintransaction.statement.model.statement.services.StatementAccessModuleService;
import br.com.dfdevforge.sisfintransaction.statement.model.statement.services.StatementAccessRegistrationService;
import br.com.dfdevforge.sisfintransaction.statement.model.statement.services.StatementExecuteEditionService;
import br.com.dfdevforge.sisfintransaction.statement.model.statement.services.StatementExecuteExclusionService;
import br.com.dfdevforge.sisfintransaction.statement.model.statement.services.StatementExecuteRegistrationService;
import br.com.dfdevforge.sisfintransaction.statement.model.statement.services.StatementExecuteSearchService;

@RestController
@RequestMapping(value = "/statement")
public class StatementResource {
	private ResourceDataEntity resourceData = new ResourceDataEntity();

	private final StatementAccessModuleService statementAccessModuleService;
	private final StatementAccessEditionService statementAccessEditionService;
	private final StatementAccessRegistrationService statementAccessRegistrationService;
	private final StatementExecuteSearchService statementExecuteSearchService;
	private final StatementExecuteEditionService statementExecuteEditionService;
	private final StatementExecuteExclusionService statementExecuteExclusionService;
	private final StatementExecuteRegistrationService statementExecuteRegistrationService;

	@Autowired
	public StatementResource(StatementAccessModuleService statementAccessModuleService, StatementAccessEditionService statementAccessEditionService, StatementAccessRegistrationService statementAccessRegistrationService, StatementExecuteSearchService statementExecuteSearchService, StatementExecuteEditionService statementExecuteEditionService, StatementExecuteExclusionService statementExecuteExclusionService, StatementExecuteRegistrationService statementExecuteRegistrationService) {
		this.statementAccessModuleService = statementAccessModuleService;
		this.statementAccessEditionService = statementAccessEditionService;
		this.statementAccessRegistrationService = statementAccessRegistrationService;
		this.statementExecuteSearchService = statementExecuteSearchService;
		this.statementExecuteEditionService = statementExecuteEditionService;
		this.statementExecuteExclusionService = statementExecuteExclusionService;
		this.statementExecuteRegistrationService = statementExecuteRegistrationService;
	}

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