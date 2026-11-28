package br.com.dfdevforge.sisfintransaction.supermarket.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dfdevforge.sisfintransaction.commons.entities.ResourceDataEntity;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.supermarket.model.measurementunit.entities.MeasurementUnitEntity;
import br.com.dfdevforge.sisfintransaction.supermarket.model.measurementunit.services.MeasurementUnitAccessEditionService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.measurementunit.services.MeasurementUnitAccessModuleService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.measurementunit.services.MeasurementUnitExecuteEditionService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.measurementunit.services.MeasurementUnitExecuteExclusionService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.measurementunit.services.MeasurementUnitExecuteRegistrationService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/measurementUnit")
public class MeasurementUnitResource {
	private final MeasurementUnitAccessModuleService measurementUnitAccessModuleService;
	private final MeasurementUnitAccessEditionService measurementUnitAccessEditionService;
	private final MeasurementUnitExecuteEditionService measurementUnitExecuteEditionService;
	private final MeasurementUnitExecuteExclusionService measurementUnitExecuteExclusionService;
	private final MeasurementUnitExecuteRegistrationService measurementUnitExecuteRegistrationService;

	@PostMapping(value = "/accessModule")
	public ResponseEntity<ResourceDataEntity> accessModule(@RequestBody MeasurementUnitEntity measurementUnit) throws BaseException {
		this.measurementUnitAccessModuleService.setParams(measurementUnit);

		ResourceDataEntity resourceData = new ResourceDataEntity();
		resourceData.setMap(this.measurementUnitAccessModuleService.execute());

		return ResponseEntity.ok(resourceData);
	}

	@PostMapping(value = "/accessEdition")
	public ResponseEntity<ResourceDataEntity> accessEdition(@RequestBody MeasurementUnitEntity measurementUnit) throws BaseException {
		this.measurementUnitAccessEditionService.setParams(measurementUnit);

		ResourceDataEntity resourceData = new ResourceDataEntity();
		resourceData.setMap(this.measurementUnitAccessEditionService.execute());

		return ResponseEntity.ok(resourceData);
	}

	@PostMapping(value = "/executeEdition")
	public ResponseEntity<ResourceDataEntity> executeEdition(@RequestBody MeasurementUnitEntity measurementUnit) throws BaseException {
		this.measurementUnitExecuteEditionService.setParams(measurementUnit);

		ResourceDataEntity resourceData = new ResourceDataEntity();
		resourceData.setMap(this.measurementUnitExecuteEditionService.execute());

		return ResponseEntity.ok(resourceData);
	}

	@PostMapping(value = "/executeExclusion")
	public ResponseEntity<ResourceDataEntity> executeExclusion(@RequestBody MeasurementUnitEntity measurementUnit) throws BaseException {
		this.measurementUnitExecuteExclusionService.setParams(measurementUnit);

		ResourceDataEntity resourceData = new ResourceDataEntity();
		resourceData.setMap(this.measurementUnitExecuteExclusionService.execute());

		return ResponseEntity.ok(resourceData);
	}

	@PostMapping(value = "/executeRegistration")
	public ResponseEntity<ResourceDataEntity> executeRegistration(@RequestBody MeasurementUnitEntity measurementUnit) throws BaseException {
		this.measurementUnitExecuteRegistrationService.setParams(measurementUnit);

		ResourceDataEntity resourceData = new ResourceDataEntity();
		resourceData.setMap(this.measurementUnitExecuteRegistrationService.execute());

		return ResponseEntity.ok(resourceData);
	}
}
