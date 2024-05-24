package br.com.dfdevforge.sisfintransaction.transaction.services.location;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.DataForEditionNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.transaction.entities.LocationEntity;
import br.com.dfdevforge.sisfintransaction.transaction.repositories.LocationRepository;

@Service
@RequestScope
@Transactional
public class LocationExecuteEditionService extends LocationBaseService implements CommonService {
	@Autowired private LocationRepository locationRepository;

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findById();
		this.editLocation();
		this.findAllLocations();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("locationRegistered", this.locationParam);
		return super.returnBusinessData();
	}

	private void findById() throws DataForEditionNotFoundException {
		LocationEntity location = this.locationRepository.findByIdentity(this.locationParam.getIdentity());

		if (location == null)
			throw new DataForEditionNotFoundException();
	}

	private void editLocation() {
		this.locationRepository.save(this.locationParam);
	}

	private void findAllLocations() {
		this.setArtifact("locationList", this.locationRepository.findByUserIdentityOrderByNameAsc(locationParam.getUserIdentity()));
	}
}