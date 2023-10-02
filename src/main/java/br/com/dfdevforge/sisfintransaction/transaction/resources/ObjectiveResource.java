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
import br.com.dfdevforge.sisfintransaction.transaction.entities.ObjectiveEntity;
import br.com.dfdevforge.sisfintransaction.transaction.services.objective.ObjectiveAccessEditionService;
import br.com.dfdevforge.sisfintransaction.transaction.services.objective.ObjectiveAccessModuleService;
import br.com.dfdevforge.sisfintransaction.transaction.services.objective.ObjectiveAccessRegistrationService;
import br.com.dfdevforge.sisfintransaction.transaction.services.objective.ObjectiveExecuteEditionService;
import br.com.dfdevforge.sisfintransaction.transaction.services.objective.ObjectiveExecuteExclusionService;
import br.com.dfdevforge.sisfintransaction.transaction.services.objective.ObjectiveExecuteRegistrationService;
import br.com.dfdevforge.sisfintransaction.transaction.services.objective.ObjectiveExecuteSearchService;

@RestController
@RequestMapping(value = "/objective")
public class ObjectiveResource {
	private ResourceDataEntity resourceData = new ResourceDataEntity();

	@Autowired private ObjectiveAccessModuleService objectiveAccessModuleService;
	@Autowired private ObjectiveAccessEditionService objectiveAccessEditionService;
	@Autowired private ObjectiveAccessRegistrationService objectiveAccessRegistrationService;

	@Autowired private ObjectiveExecuteSearchService objectiveExecuteSearchService;
	@Autowired private ObjectiveExecuteEditionService objectiveExecuteEditionService;
	@Autowired private ObjectiveExecuteExclusionService objectiveExecuteExclusionService;
	@Autowired private ObjectiveExecuteRegistrationService objectiveExecuteRegistrationService;

	@PostMapping(value = "/accessModule")
	public ResponseEntity<ResourceDataEntity> accessModule(@RequestBody ObjectiveEntity objective, @RequestParam String token) throws BaseException {
		this.objectiveAccessModuleService.setParams(objective, token);
		this.resourceData.setMap(this.objectiveAccessModuleService.execute());

		return ResponseEntity.ok(this.resourceData);
	}

	@PostMapping(value = "/accessEdition")
	public ResponseEntity<ResourceDataEntity> accessEdition(@RequestBody ObjectiveEntity objective, @RequestParam String token) throws BaseException {
		this.objectiveAccessEditionService.setParams(objective, token);
		this.resourceData.setMap(this.objectiveAccessEditionService.execute());

		return ResponseEntity.ok(this.resourceData);
	}

	@PostMapping(value = "/accessRegistration")
	public ResponseEntity<ResourceDataEntity> accessRegistration(@RequestBody ObjectiveEntity objective, @RequestParam String token) throws BaseException {
		this.objectiveAccessRegistrationService.setParams(objective, token);
		this.resourceData.setMap(this.objectiveAccessRegistrationService.execute());

		return ResponseEntity.ok(this.resourceData);
	}

	@PostMapping(value = "/executeSearch")
	public ResponseEntity<ResourceDataEntity> executeSearch(@RequestBody ObjectiveEntity objective, @RequestParam String token) throws BaseException {
		this.objectiveExecuteSearchService.setParams(objective, token);
		this.resourceData.setMap(this.objectiveExecuteSearchService.execute());

		return ResponseEntity.ok(this.resourceData);
	}

	@PostMapping(value = "/executeEdition")
	public ResponseEntity<ResourceDataEntity> executeEdition(@RequestBody ObjectiveEntity objective, @RequestParam String token) throws BaseException {
		this.objectiveExecuteEditionService.setParams(objective, token);
		this.resourceData.setMap(this.objectiveExecuteEditionService.execute());

		return ResponseEntity.ok(this.resourceData);
	}

	@PostMapping(value = "/executeExclusion")
	public ResponseEntity<ResourceDataEntity> executeExclusion(@RequestBody ObjectiveEntity objective, @RequestParam String token) throws BaseException {
		this.objectiveExecuteExclusionService.setParams(objective, token);
		this.resourceData.setMap(this.objectiveExecuteExclusionService.execute());

		return ResponseEntity.ok(this.resourceData);
	}

	@PostMapping(value = "/executeRegistration")
	public ResponseEntity<ResourceDataEntity> executeRegistration(@RequestBody ObjectiveEntity objective, @RequestParam String token) throws BaseException {
		this.objectiveExecuteRegistrationService.setParams(objective, token);
		this.resourceData.setMap(this.objectiveExecuteRegistrationService.execute());

		return ResponseEntity.ok(this.resourceData);
	}
}