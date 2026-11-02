package br.com.dfdevforge.sisfintransaction.supermarket.model.measurementunit.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.DataForEditionNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.measurementunit.entities.MeasurementUnitEntity;
import br.com.dfdevforge.sisfintransaction.supermarket.model.measurementunit.repositories.MeasurementUnitRepository;

@Service
@RequestScope
public class MeasurementUnitExecuteEditionService extends MeasurementUnitBaseService implements CommonService {
	private final MeasurementUnitRepository measurementUnitRepository;

	@Autowired
	public MeasurementUnitExecuteEditionService(MeasurementUnitRepository measurementUnitRepository) {
		this.measurementUnitRepository = measurementUnitRepository;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findById();
		this.editMeasurementUnit();
		this.findAllMeasurementUnits();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("measurementUnitRegistered", this.measurementUnitParam);
		return super.returnBusinessData();
	}

	private void findById() throws DataForEditionNotFoundException {
		MeasurementUnitEntity measurementUnit = this.measurementUnitRepository.findByIdentity(this.measurementUnitParam.getIdentity());

		if (measurementUnit == null)
			throw new DataForEditionNotFoundException();
	}

	private void editMeasurementUnit() {
		this.measurementUnitRepository.save(this.measurementUnitParam);
	}

	private void findAllMeasurementUnits() {
		this.setArtifact("measurementUnitList", this.measurementUnitRepository.findByUserIdentityOrderByNameAsc(measurementUnitParam.getUserIdentity()));
	}
}