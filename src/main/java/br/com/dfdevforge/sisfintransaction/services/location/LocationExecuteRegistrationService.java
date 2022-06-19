package br.com.dfdevforge.sisfintransaction.services.location;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dfdevforge.common.exceptions.BaseException;
import br.com.dfdevforge.common.services.CommonService;
import br.com.dfdevforge.sisfintransaction.exceptions.RequiredFieldNotFoundException;
import br.com.dfdevforge.sisfintransaction.repositories.LocationRepository;

@Service
public class LocationExecuteRegistrationService extends LocationBaseService implements CommonService {
	@Autowired private LocationRepository locationRepository;

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

		if (this.locationParam.getCnpj() == null || this.locationParam.getCnpj().equals(""))
			errorList.add("Please, enter CNPJ.");
		if (this.locationParam.getName() == null || this.locationParam.getName().equals(""))
			errorList.add("Please, enter name.");
		if (this.locationParam.getBranch() == null || this.locationParam.getBranch().equals(""))
			errorList.add("Please, enter branch name.");
		if (this.locationParam.getUserIdentity() == null)
			errorList.add("Please, the location need to be associated with a user.");

		if (errorList != null && !errorList.isEmpty())
			throw new RequiredFieldNotFoundException("Required Field Not Found", errorList);
	}

	private void saveLocation() throws BaseException {
		this.locationRepository.save(this.locationParam);
	}

	private void findAllLocations() {
		this.setArtifact("locationList", this.locationRepository.findByUserIdentity(locationParam.getUserIdentity()));
	}
}