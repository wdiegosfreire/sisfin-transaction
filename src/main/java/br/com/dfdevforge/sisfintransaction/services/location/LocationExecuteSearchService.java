package br.com.dfdevforge.sisfintransaction.services.location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dfdevforge.common.exceptions.BaseException;
import br.com.dfdevforge.common.services.CommonService;
import br.com.dfdevforge.sisfintransaction.repositories.LocationRepository;
import br.com.dfdevforge.sisfintransaction.repositories.LocationRepositoryCustomized;

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
			this.setArtifact("locationList", this.locationRepository.findAll());
		else
			this.setArtifact("locationList", this.locationRepositoryCustomized.searchInAllProperties(this.locationParam));
	}
}