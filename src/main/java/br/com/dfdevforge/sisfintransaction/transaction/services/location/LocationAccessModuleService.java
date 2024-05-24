package br.com.dfdevforge.sisfintransaction.transaction.services.location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.transaction.repositories.LocationRepository;

@Service
@RequestScope
@Transactional
public class LocationAccessModuleService extends LocationBaseService implements CommonService {
	private final LocationRepository locationRepository;

	@Autowired
	public LocationAccessModuleService(LocationRepository locationRepository) {
		this.locationRepository = locationRepository;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findAllLocations();
	}

	private void findAllLocations() {
		this.setArtifact("locationList", this.locationRepository.findByUserIdentityOrderByNameAsc(this.locationParam.getUserIdentity()));
	}
}