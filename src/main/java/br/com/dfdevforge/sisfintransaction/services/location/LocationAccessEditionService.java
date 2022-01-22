package br.com.dfdevforge.sisfintransaction.services.location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dfdevforge.common.exceptions.BaseException;
import br.com.dfdevforge.common.services.CommonService;
import br.com.dfdevforge.sisfintransaction.entities.LocationEntity;
import br.com.dfdevforge.sisfintransaction.exceptions.DataForEditionNotFoundException;
import br.com.dfdevforge.sisfintransaction.repositories.LocationRepository;

@Service
public class LocationAccessEditionService extends LocationBaseService implements CommonService {
	@Autowired private LocationRepository locationRepository;

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findById();
	}

	private void findById() throws DataForEditionNotFoundException {
		LocationEntity location = this.locationRepository.findByIdentity(this.locationParam.getIdentity());

		if (location == null)
			throw new DataForEditionNotFoundException();

		this.setArtifact("location", location);
	}
}