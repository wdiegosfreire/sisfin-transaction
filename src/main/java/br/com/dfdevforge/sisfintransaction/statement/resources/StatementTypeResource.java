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
import br.com.dfdevforge.sisfintransaction.statement.entities.StatementTypeEntity;
import br.com.dfdevforge.sisfintransaction.statement.service.statementtype.StatementTypeAccessEditionService;
import br.com.dfdevforge.sisfintransaction.statement.service.statementtype.StatementTypeAccessModuleService;
import br.com.dfdevforge.sisfintransaction.statement.service.statementtype.StatementTypeAccessRegistrationService;
import br.com.dfdevforge.sisfintransaction.statement.service.statementtype.StatementTypeExecuteEditionService;
import br.com.dfdevforge.sisfintransaction.statement.service.statementtype.StatementTypeExecuteExclusionService;
import br.com.dfdevforge.sisfintransaction.statement.service.statementtype.StatementTypeExecuteRegistrationService;
import br.com.dfdevforge.sisfintransaction.statement.service.statementtype.StatementTypeExecuteSearchService;

@RestController
@RequestMapping(value = "/statementType")
public class StatementTypeResource {
	private ResourceDataEntity resourceData = new ResourceDataEntity();

	@Autowired private StatementTypeAccessModuleService statementTypeAccessModuleService;
	@Autowired private StatementTypeAccessEditionService statementTypeAccessEditionService;
	@Autowired private StatementTypeExecuteSearchService statementTypeExecuteSearchService;
	@Autowired private StatementTypeExecuteEditionService statementTypeExecuteEditionService;
	@Autowired private StatementTypeExecuteExclusionService statementTypeExecuteExclusionService;
	@Autowired private StatementTypeAccessRegistrationService statementTypeAccessRegistrationService;
	@Autowired private StatementTypeExecuteRegistrationService statementTypeExecuteRegistrationService;
	

	@PostMapping(value = "/accessModule")
	public ResponseEntity<ResourceDataEntity> accessModule(@RequestBody StatementTypeEntity statementType, @RequestParam String token) throws BaseException {
		this.statementTypeAccessModuleService.setParams(statementType, token);
		this.resourceData.setMap(this.statementTypeAccessModuleService.execute());

		return ResponseEntity.ok(this.resourceData);
	}

	@PostMapping(value = "/accessEdition")
	public ResponseEntity<ResourceDataEntity> accessEdition(@RequestBody StatementTypeEntity statementType, @RequestParam String token) throws BaseException {
		this.statementTypeAccessEditionService.setParams(statementType, token);
		this.resourceData.setMap(this.statementTypeAccessEditionService.execute());

		return ResponseEntity.ok(this.resourceData);
	}

	@PostMapping(value = "/executeSearch")
	public ResponseEntity<ResourceDataEntity> executeSearch(@RequestBody StatementTypeEntity statementType, @RequestParam String token) throws BaseException {
		this.statementTypeExecuteSearchService.setParams(statementType, token);
		this.resourceData.setMap(this.statementTypeExecuteSearchService.execute());

		return ResponseEntity.ok(this.resourceData);
	}

	@PostMapping(value = "/executeEdition")
	public ResponseEntity<ResourceDataEntity> executeEdition(@RequestBody StatementTypeEntity statementType, @RequestParam String token) throws BaseException {
		this.statementTypeExecuteEditionService.setParams(statementType, token);
		this.resourceData.setMap(this.statementTypeExecuteEditionService.execute());

		return ResponseEntity.ok(this.resourceData);
	}

	@PostMapping(value = "/executeExclusion")
	public ResponseEntity<ResourceDataEntity> executeExclusion(@RequestBody StatementTypeEntity statementType, @RequestParam String token) throws BaseException {
		this.statementTypeExecuteExclusionService.setParams(statementType, token);
		this.resourceData.setMap(this.statementTypeExecuteExclusionService.execute());

		return ResponseEntity.ok(this.resourceData);
	}

	@PostMapping(value = "/accessRegistration")
	public ResponseEntity<ResourceDataEntity> accessRegistration(@RequestBody StatementTypeEntity statementType, @RequestParam String token) throws BaseException {
		this.statementTypeAccessRegistrationService.setParams(statementType, token);
		this.resourceData.setMap(this.statementTypeAccessRegistrationService.execute());

		return ResponseEntity.ok(this.resourceData);
	}

	@PostMapping(value = "/executeRegistration")
	public ResponseEntity<ResourceDataEntity> executeRegistration(@RequestBody StatementTypeEntity statementType, @RequestParam String token) throws BaseException {
		this.statementTypeExecuteRegistrationService.setParams(statementType, token);
		this.resourceData.setMap(this.statementTypeExecuteRegistrationService.execute());

		return ResponseEntity.ok(this.resourceData);
	}
}