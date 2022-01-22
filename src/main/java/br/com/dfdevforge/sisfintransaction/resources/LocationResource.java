package br.com.dfdevforge.sisfintransaction.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dfdevforge.common.entities.ResourceDataEntity;
import br.com.dfdevforge.common.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.entities.LocationEntity;
import br.com.dfdevforge.sisfintransaction.services.location.LocationAccessEditionService;
import br.com.dfdevforge.sisfintransaction.services.location.LocationAccessModuleService;
import br.com.dfdevforge.sisfintransaction.services.location.LocationExecuteEditionService;
import br.com.dfdevforge.sisfintransaction.services.location.LocationExecuteExclusionService;
import br.com.dfdevforge.sisfintransaction.services.location.LocationExecuteRegistrationService;
import br.com.dfdevforge.sisfintransaction.services.location.LocationExecuteSearchService;

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
	public ResponseEntity<ResourceDataEntity> accessModule(@RequestBody LocationEntity location) throws BaseException {
		this.locationAccessModuleService.setEntity(location);
		this.resourceData.setMap(this.locationAccessModuleService.execute());

		return ResponseEntity.ok(this.resourceData);
	}

	@PostMapping(value = "/accessEdition")
	public ResponseEntity<ResourceDataEntity> accessEdition(@RequestBody LocationEntity location) throws BaseException {
		this.locationAccessEditionService.setEntity(location);
		this.resourceData.setMap(this.locationAccessEditionService.execute());
		
		return ResponseEntity.ok(this.resourceData);
	}

	@PostMapping(value = "/executeSearch")
	public ResponseEntity<ResourceDataEntity> executeSearch(@RequestBody LocationEntity location) throws BaseException {
		this.locationExecuteSearchService.setEntity(location);
		this.resourceData.setMap(this.locationExecuteSearchService.execute());
		
		return ResponseEntity.ok(this.resourceData);
	}

	@PostMapping(value = "/executeEdition")
	public ResponseEntity<ResourceDataEntity> executeEdition(@RequestBody LocationEntity location) throws BaseException {
		this.locationExecuteEditionService.setEntity(location);
		this.resourceData.setMap(this.locationExecuteEditionService.execute());
		
		return ResponseEntity.ok(this.resourceData);
	}

	@PostMapping(value = "/executeExclusion")
	public ResponseEntity<ResourceDataEntity> executeExclusion(@RequestBody LocationEntity location) throws BaseException {
		this.locationExecuteExclusionService.setEntity(location);
		this.resourceData.setMap(this.locationExecuteExclusionService.execute());
		
		return ResponseEntity.ok(this.resourceData);
	}

	@PostMapping(value = "/executeRegistration")
	public ResponseEntity<ResourceDataEntity> executeRegistration(@RequestBody LocationEntity location) throws BaseException {
		this.locationExecuteRegistrationService.setEntity(location);
		this.resourceData.setMap(this.locationExecuteRegistrationService.execute());

		return ResponseEntity.ok(this.resourceData);
	}
}