package br.com.dfdevforge.sisfintransaction.transaction.model.location.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.RequiredFieldNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.transaction.model.location.repositories.LocationRepository;

@Service
@RequestScope
@Transactional
public class LocationExecuteRegistrationService extends LocationBaseService implements CommonService {
	private final LocationRepository locationRepository;

	@Autowired
	public LocationExecuteRegistrationService(LocationRepository locationRepository) {
		this.locationRepository = locationRepository;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.checkRequiredFields();
		this.saveLocation();
		this.findAllLocations();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("locationRegistered", this.locationParam);
		return super.returnBusinessData();
	}

	private void checkRequiredFields() throws RequiredFieldNotFoundException {
		List<String> errorList = new ArrayList<>();

		if (this.locationParam.getName() == null || this.locationParam.getName().equals(""))
			errorList.add("Please, enter name.");
		if (this.locationParam.getUserIdentity() == null)
			errorList.add("Please, the location need to be associated with a user.");

		if (!errorList.isEmpty())
			throw new RequiredFieldNotFoundException("Required Field Not Found", errorList);
	}

	private void saveLocation() {
		this.locationRepository.save(this.locationParam);
	}

	private void findAllLocations() {
		this.setArtifact("locationList", this.locationRepository.findByUserIdentityOrderByNameAsc(locationParam.getUserIdentity()));
	}
}