package br.com.dfdevforge.sisfintransaction.transaction.model.location.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.transaction.model.location.repositories.LocationRepository;
import br.com.dfdevforge.sisfintransaction.transaction.model.location.repositories.LocationRepositoryCustomized;

@Service
@RequestScope
@Transactional
public class LocationExecuteSearchService extends LocationBaseService implements CommonService {
	private final LocationRepository locationRepository;
	private final LocationRepositoryCustomized locationRepositoryCustomized;

	@Autowired
	public LocationExecuteSearchService(LocationRepository locationRepository, LocationRepositoryCustomized locationRepositoryCustomized) {
		this.locationRepository = locationRepository;
		this.locationRepositoryCustomized = locationRepositoryCustomized;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findAllLocations();
	}

	private void findAllLocations() {
		if (this.locationParam.getFilter() == null || this.locationParam.getFilter().contentEquals(""))
			this.setArtifact("locationList", this.locationRepository.findByUserIdentityOrderByNameAsc(locationParam.getUserIdentity()));
		else
			this.setArtifact("locationList", this.locationRepositoryCustomized.searchInAllProperties(this.locationParam));
	}
}