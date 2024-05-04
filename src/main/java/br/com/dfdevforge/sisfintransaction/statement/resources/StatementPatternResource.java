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
import br.com.dfdevforge.sisfintransaction.statement.entities.StatementPatternEntity;
import br.com.dfdevforge.sisfintransaction.statement.service.statementpattern.StatementPatternAccessEditionService;
import br.com.dfdevforge.sisfintransaction.statement.service.statementpattern.StatementPatternAccessModuleService;
import br.com.dfdevforge.sisfintransaction.statement.service.statementpattern.StatementPatternAccessRegistrationService;
import br.com.dfdevforge.sisfintransaction.statement.service.statementpattern.StatementPatternExecuteEditionService;
import br.com.dfdevforge.sisfintransaction.statement.service.statementpattern.StatementPatternExecuteExclusionService;
import br.com.dfdevforge.sisfintransaction.statement.service.statementpattern.StatementPatternExecuteRegistrationService;
import br.com.dfdevforge.sisfintransaction.statement.service.statementpattern.StatementPatternExecuteSearchService;

@RestController
@RequestMapping(value = "/statementPattern")
public class StatementPatternResource {
	private ResourceDataEntity resourceData = new ResourceDataEntity();

	private final StatementPatternAccessModuleService statementPatternAccessModuleService;
	private final StatementPatternAccessEditionService statementPatternAccessEditionService;
	private final StatementPatternExecuteSearchService statementPatternExecuteSearchService;
	private final StatementPatternExecuteEditionService statementPatternExecuteEditionService;
	private final StatementPatternExecuteExclusionService statementPatternExecuteExclusionService;
	private final StatementPatternAccessRegistrationService statementPatternAccessRegistrationService;
	private final StatementPatternExecuteRegistrationService statementPatternExecuteRegistrationService;

	@Autowired
	public StatementPatternResource(StatementPatternAccessModuleService statementPatternAccessModuleService, StatementPatternAccessEditionService statementPatternAccessEditionService, StatementPatternExecuteSearchService statementPatternExecuteSearchService, StatementPatternExecuteEditionService statementPatternExecuteEditionService, StatementPatternExecuteExclusionService statementPatternExecuteExclusionService, StatementPatternAccessRegistrationService statementPatternAccessRegistrationService, StatementPatternExecuteRegistrationService statementPatternExecuteRegistrationService) {
		this.statementPatternAccessModuleService = statementPatternAccessModuleService;
		this.statementPatternAccessEditionService = statementPatternAccessEditionService;
		this.statementPatternExecuteSearchService = statementPatternExecuteSearchService;
		this.statementPatternExecuteEditionService = statementPatternExecuteEditionService;
		this.statementPatternExecuteExclusionService = statementPatternExecuteExclusionService;
		this.statementPatternAccessRegistrationService = statementPatternAccessRegistrationService;
		this.statementPatternExecuteRegistrationService = statementPatternExecuteRegistrationService;
	}

	@PostMapping(value = "/accessModule")
	public ResponseEntity<ResourceDataEntity> accessModule(@RequestBody StatementPatternEntity statementPattern, @RequestParam String token) throws BaseException {
		this.statementPatternAccessModuleService.setParams(statementPattern, token);
		this.resourceData.setMap(this.statementPatternAccessModuleService.execute());

		return ResponseEntity.ok(this.resourceData);
	}

	@PostMapping(value = "/accessEdition")
	public ResponseEntity<ResourceDataEntity> accessEdition(@RequestBody StatementPatternEntity statementPattern, @RequestParam String token) throws BaseException {
		this.statementPatternAccessEditionService.setParams(statementPattern, token);
		this.resourceData.setMap(this.statementPatternAccessEditionService.execute());

		return ResponseEntity.ok(this.resourceData);
	}

	@PostMapping(value = "/executeSearch")
	public ResponseEntity<ResourceDataEntity> executeSearch(@RequestBody StatementPatternEntity statementPattern, @RequestParam String token) throws BaseException {
		this.statementPatternExecuteSearchService.setParams(statementPattern, token);
		this.resourceData.setMap(this.statementPatternExecuteSearchService.execute());

		return ResponseEntity.ok(this.resourceData);
	}

	@PostMapping(value = "/executeEdition")
	public ResponseEntity<ResourceDataEntity> executeEdition(@RequestBody StatementPatternEntity statementPattern, @RequestParam String token) throws BaseException {
		this.statementPatternExecuteEditionService.setParams(statementPattern, token);
		this.resourceData.setMap(this.statementPatternExecuteEditionService.execute());

		return ResponseEntity.ok(this.resourceData);
	}

	@PostMapping(value = "/executeExclusion")
	public ResponseEntity<ResourceDataEntity> executeExclusion(@RequestBody StatementPatternEntity statementPattern, @RequestParam String token) throws BaseException {
		this.statementPatternExecuteExclusionService.setParams(statementPattern, token);
		this.resourceData.setMap(this.statementPatternExecuteExclusionService.execute());

		return ResponseEntity.ok(this.resourceData);
	}

	@PostMapping(value = "/accessRegistration")
	public ResponseEntity<ResourceDataEntity> accessRegistration(@RequestBody StatementPatternEntity statementPattern, @RequestParam String token) throws BaseException {
		this.statementPatternAccessRegistrationService.setParams(statementPattern, token);
		this.resourceData.setMap(this.statementPatternAccessRegistrationService.execute());

		return ResponseEntity.ok(this.resourceData);
	}

	@PostMapping(value = "/executeRegistration")
	public ResponseEntity<ResourceDataEntity> executeRegistration(@RequestBody StatementPatternEntity statementPattern, @RequestParam String token) throws BaseException {
		this.statementPatternExecuteRegistrationService.setParams(statementPattern, token);
		this.resourceData.setMap(this.statementPatternExecuteRegistrationService.execute());

		return ResponseEntity.ok(this.resourceData);
	}
}