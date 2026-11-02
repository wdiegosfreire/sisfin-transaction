package br.com.dfdevforge.sisfintransaction.supermarket.model.measurementunit.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.RequiredFieldNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.measurementunit.repositories.MeasurementUnitRepository;

@Service
@RequestScope
public class MeasurementUnitExecuteRegistrationService extends MeasurementUnitBaseService implements CommonService {
	private final MeasurementUnitRepository measurementUnitRepository;

	@Autowired
	public MeasurementUnitExecuteRegistrationService(MeasurementUnitRepository measurementUnitRepository) {
		this.measurementUnitRepository = measurementUnitRepository;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.checkRequiredFields();
		this.saveMeasurementUnit();
		this.findAllMeasurementUnits();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("measurementUnitRegistered", this.measurementUnitParam);
		return super.returnBusinessData();
	}

	private void checkRequiredFields() throws RequiredFieldNotFoundException {
		List<String> errorList = new ArrayList<>();

		if (StringUtils.isBlank(this.measurementUnitParam.getName()))
			errorList.add("Please, enter name.");
		if (this.measurementUnitParam.getUserIdentity() == null)
			errorList.add("Please, the measurement unit need to be associated with a user.");

		if (!errorList.isEmpty())
			throw new RequiredFieldNotFoundException("Required Field Not Found", errorList);
	}

	private void saveMeasurementUnit() {
		this.measurementUnitRepository.save(this.measurementUnitParam);
	}

	private void findAllMeasurementUnits() {
		this.setArtifact("measurementUnitList", this.measurementUnitRepository.findByUserIdentityOrderByNameAsc(measurementUnitParam.getUserIdentity()));
	}
}