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
import br.com.dfdevforge.sisfintransaction.entities.ObjectiveMovementEntity;
import br.com.dfdevforge.sisfintransaction.services.objectivemovement.ObjectiveMovementAccessEditionService;
import br.com.dfdevforge.sisfintransaction.services.objectivemovement.ObjectiveMovementAccessModuleService;
import br.com.dfdevforge.sisfintransaction.services.objectivemovement.ObjectiveMovementExecuteEditionService;
import br.com.dfdevforge.sisfintransaction.services.objectivemovement.ObjectiveMovementExecuteExclusionService;
import br.com.dfdevforge.sisfintransaction.services.objectivemovement.ObjectiveMovementExecuteRegistrationService;
import br.com.dfdevforge.sisfintransaction.services.objectivemovement.ObjectiveMovementExecuteSearchService;

@RestController
@RequestMapping(value = "/objectiveMovement")
public class ObjectiveMovementResource {
	private ResourceDataEntity resourceData = new ResourceDataEntity();

	@Autowired private ObjectiveMovementAccessModuleService objectiveMovementAccessModuleService;
	@Autowired private ObjectiveMovementAccessEditionService objectiveMovementAccessEditionService;
	@Autowired private ObjectiveMovementExecuteSearchService objectiveMovementExecuteSearchService;
	@Autowired private ObjectiveMovementExecuteEditionService objectiveMovementExecuteEditionService;
	@Autowired private ObjectiveMovementExecuteExclusionService objectiveMovementExecuteExclusionService;
	@Autowired private ObjectiveMovementExecuteRegistrationService objectiveMovementExecuteRegistrationService;


	@PostMapping(value = "/accessModule")
	public ResponseEntity<ResourceDataEntity> accessModule(@RequestBody ObjectiveMovementEntity objectiveMovement, @RequestParam String token) throws BaseException {
		System.out.println(objectiveMovement.getPaymentDate());

		this.objectiveMovementAccessModuleService.setParams(objectiveMovement, token);
		this.resourceData.setMap(this.objectiveMovementAccessModuleService.execute());

		return ResponseEntity.ok(this.resourceData);
	}

	@PostMapping(value = "/accessEdition")
	public ResponseEntity<ResourceDataEntity> accessEdition(@RequestBody ObjectiveMovementEntity objectiveMovement, @RequestParam String token) throws BaseException {
		this.objectiveMovementAccessEditionService.setParams(objectiveMovement, token);
		this.resourceData.setMap(this.objectiveMovementAccessEditionService.execute());

		return ResponseEntity.ok(this.resourceData);
	}

	@PostMapping(value = "/executeSearch")
	public ResponseEntity<ResourceDataEntity> executeSearch(@RequestBody ObjectiveMovementEntity objectiveMovement, @RequestParam String token) throws BaseException {
		this.objectiveMovementExecuteSearchService.setParams(objectiveMovement, token);
		this.resourceData.setMap(this.objectiveMovementExecuteSearchService.execute());

		return ResponseEntity.ok(this.resourceData);
	}

	@PostMapping(value = "/executeEdition")
	public ResponseEntity<ResourceDataEntity> executeEdition(@RequestBody ObjectiveMovementEntity objectiveMovement, @RequestParam String token) throws BaseException {
		this.objectiveMovementExecuteEditionService.setParams(objectiveMovement, token);
		this.resourceData.setMap(this.objectiveMovementExecuteEditionService.execute());

		return ResponseEntity.ok(this.resourceData);
	}

	@PostMapping(value = "/executeExclusion")
	public ResponseEntity<ResourceDataEntity> executeExclusion(@RequestBody ObjectiveMovementEntity objectiveMovement, @RequestParam String token) throws BaseException {
		this.objectiveMovementExecuteExclusionService.setParams(objectiveMovement, token);
		this.resourceData.setMap(this.objectiveMovementExecuteExclusionService.execute());

		return ResponseEntity.ok(this.resourceData);
	}

	@PostMapping(value = "/executeRegistration")
	public ResponseEntity<ResourceDataEntity> executeRegistration(@RequestBody ObjectiveMovementEntity objectiveMovement, @RequestParam String token) throws BaseException {
		this.objectiveMovementExecuteRegistrationService.setParams(objectiveMovement, token);
		this.resourceData.setMap(this.objectiveMovementExecuteRegistrationService.execute());

		return ResponseEntity.ok(this.resourceData);
	}
}