package br.com.dfdevforge.sisfintransaction.supermarket.model.measurementunit.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.measurementunit.repositories.MeasurementUnitRepository;

@Service
@RequestScope
public class MeasurementUnitAccessModuleService extends MeasurementUnitBaseService implements CommonService {
	private final MeasurementUnitRepository measurementUnitRepository;

	@Autowired
	public MeasurementUnitAccessModuleService(MeasurementUnitRepository measurementUnitRepository) {
		this.measurementUnitRepository = measurementUnitRepository;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findAllMeasurementUnits();
	}

	private void findAllMeasurementUnits() {
		this.setArtifact("measurementUnitList", this.measurementUnitRepository.findByUserIdentityOrderByNameAsc(this.measurementUnitParam.getUserIdentity()));
	}
}