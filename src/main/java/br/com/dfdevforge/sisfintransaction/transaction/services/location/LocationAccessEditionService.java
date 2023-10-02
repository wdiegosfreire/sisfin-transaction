package br.com.dfdevforge.sisfintransaction.transaction.services.location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.DataForEditionNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.transaction.entities.LocationEntity;
import br.com.dfdevforge.sisfintransaction.transaction.repositories.LocationRepository;

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