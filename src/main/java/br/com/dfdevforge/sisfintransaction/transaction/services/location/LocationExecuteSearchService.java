package br.com.dfdevforge.sisfintransaction.transaction.services.location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.transaction.repositories.LocationRepository;
import br.com.dfdevforge.sisfintransaction.transaction.repositories.LocationRepositoryCustomized;

@Service
public class LocationExecuteSearchService extends LocationBaseService implements CommonService {
	@Autowired private LocationRepository locationRepository;
	@Autowired private LocationRepositoryCustomized locationRepositoryCustomized;

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findAllLocations();
	}

	private void findAllLocations() {
		if (this.locationParam.getFilter() == null || this.locationParam.getFilter().contentEquals(""))
			this.setArtifact("locationList", this.locationRepository.findByUserIdentityOrderByNameAscBranchAsc(locationParam.getUserIdentity()));
		else
			this.setArtifact("locationList", this.locationRepositoryCustomized.searchInAllProperties(this.locationParam));
	}
}