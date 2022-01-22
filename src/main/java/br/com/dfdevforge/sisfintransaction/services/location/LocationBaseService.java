package br.com.dfdevforge.sisfintransaction.services.location;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.dfdevforge.common.services.BaseService;
import br.com.dfdevforge.sisfintransaction.entities.LocationEntity;
import br.com.dfdevforge.sisfintransaction.feignclients.UserFeignClient;

public abstract class LocationBaseService extends BaseService {
	protected LocationEntity locationParam;

	@Autowired private UserFeignClient userFeignClient;

	public void setEntity(LocationEntity locationEntity) {
		this.locationParam = locationEntity;
	}

	@Override
	public void validateUserAccess() {
		this.userFeignClient.findByIdentity(this.locationParam.getUserIdentity());
	}
}