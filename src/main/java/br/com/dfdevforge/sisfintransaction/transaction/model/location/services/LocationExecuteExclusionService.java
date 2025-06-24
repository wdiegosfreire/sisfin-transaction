package br.com.dfdevforge.sisfintransaction.transaction.model.location.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.DataForExclusionNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.transaction.model.location.entities.LocationEntity;
import br.com.dfdevforge.sisfintransaction.transaction.model.location.repositories.LocationRepository;

@Service
@RequestScope
@Transactional
public class LocationExecuteExclusionService extends LocationBaseService implements CommonService {
	private final LocationRepository locationRepository;

	@Autowired
	public LocationExecuteExclusionService(LocationRepository locationRepository) {
		this.locationRepository = locationRepository;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findById();
		this.deleteLocation();
		this.findAllLocations();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("locationRegistered", this.locationParam);
		return super.returnBusinessData();
	}

	private void findById() throws DataForExclusionNotFoundException {
		LocationEntity location = this.locationRepository.findByIdentity(this.locationParam.getIdentity());

		if (location == null)
			throw new DataForExclusionNotFoundException();
	}

	private void deleteLocation() {
		this.locationRepository.delete(this.locationParam);
	}

	private void findAllLocations() {
		this.setArtifact("locationList", this.locationRepository.findByUserIdentityOrderByNameAsc(locationParam.getUserIdentity()));
	}
}