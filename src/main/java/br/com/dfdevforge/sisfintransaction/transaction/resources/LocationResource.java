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
import br.com.dfdevforge.sisfintransaction.transaction.entities.LocationEntity;
import br.com.dfdevforge.sisfintransaction.transaction.services.location.LocationAccessEditionService;
import br.com.dfdevforge.sisfintransaction.transaction.services.location.LocationAccessModuleService;
import br.com.dfdevforge.sisfintransaction.transaction.services.location.LocationExecuteEditionService;
import br.com.dfdevforge.sisfintransaction.transaction.services.location.LocationExecuteExclusionService;
import br.com.dfdevforge.sisfintransaction.transaction.services.location.LocationExecuteRegistrationService;
import br.com.dfdevforge.sisfintransaction.transaction.services.location.LocationExecuteSearchService;

@RestController
@RequestMapping(value = "/location")
public class LocationResource {
	private ResourceDataEntity resourceData = new ResourceDataEntity();

	@Autowired private LocationAccessModuleService locationAccessModuleService;
	@Autowired private LocationAccessEditionService locationAccessEditionService;
	@Autowired private LocationExecuteSearchService locationExecuteSearchService;
	@Autowired private LocationExecuteEditionService locationExecuteEditionService;
	@Autowired private LocationExecuteExclusionService locationExecuteExclusionService;
	@Autowired private LocationExecuteRegistrationService locationExecuteRegistrationService;


	@PostMapping(value = "/accessModule")
	public ResponseEntity<ResourceDataEntity> accessModule(@RequestBody LocationEntity location, @RequestParam String token) throws BaseException {
		this.locationAccessModuleService.setParams(location, token);
		this.resourceData.setMap(this.locationAccessModuleService.execute());

		return ResponseEntity.ok(this.resourceData);
	}

	@PostMapping(value = "/accessEdition")
	public ResponseEntity<ResourceDataEntity> accessEdition(@RequestBody LocationEntity location, @RequestParam String token) throws BaseException {
		this.locationAccessEditionService.setParams(location, token);
		this.resourceData.setMap(this.locationAccessEditionService.execute());

		return ResponseEntity.ok(this.resourceData);
	}

	@PostMapping(value = "/executeSearch")
	public ResponseEntity<ResourceDataEntity> executeSearch(@RequestBody LocationEntity location, @RequestParam String token) throws BaseException {
		this.locationExecuteSearchService.setParams(location, token);
		this.resourceData.setMap(this.locationExecuteSearchService.execute());

		return ResponseEntity.ok(this.resourceData);
	}

	@PostMapping(value = "/executeEdition")
	public ResponseEntity<ResourceDataEntity> executeEdition(@RequestBody LocationEntity location, @RequestParam String token) throws BaseException {
		this.locationExecuteEditionService.setParams(location, token);
		this.resourceData.setMap(this.locationExecuteEditionService.execute());

		return ResponseEntity.ok(this.resourceData);
	}

	@PostMapping(value = "/executeExclusion")
	public ResponseEntity<ResourceDataEntity> executeExclusion(@RequestBody LocationEntity location, @RequestParam String token) throws BaseException {
		this.locationExecuteExclusionService.setParams(location, token);
		this.resourceData.setMap(this.locationExecuteExclusionService.execute());

		return ResponseEntity.ok(this.resourceData);
	}

	@PostMapping(value = "/executeRegistration")
	public ResponseEntity<ResourceDataEntity> executeRegistration(@RequestBody LocationEntity location, @RequestParam String token) throws BaseException {
		this.locationExecuteRegistrationService.setParams(location, token);
		this.resourceData.setMap(this.locationExecuteRegistrationService.execute());

		return ResponseEntity.ok(this.resourceData);
	}
}