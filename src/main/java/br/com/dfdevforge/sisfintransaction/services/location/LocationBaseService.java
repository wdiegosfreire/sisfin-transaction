package br.com.dfdevforge.sisfintransaction.services.location;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.dfdevforge.common.exceptions.BaseException;
import br.com.dfdevforge.common.services.BaseService;
import br.com.dfdevforge.sisfintransaction.entities.LocationEntity;
import br.com.dfdevforge.sisfintransaction.entities.UserEntity;
import br.com.dfdevforge.sisfintransaction.exceptions.UserUnauthorizedException;
import br.com.dfdevforge.sisfintransaction.feignclients.UserFeignClient;

public abstract class LocationBaseService extends BaseService {
	protected LocationEntity locationParam;

	@Autowired private UserFeignClient userFeignClient;

	public void setParams(LocationEntity locationEntity, String token) {
		this.locationParam = locationEntity;
		this.token = token;
	}

	@Override
	public void validateUserAccess() throws BaseException {
		UserEntity userValidatedByToken = this.userFeignClient.validateToken(this.token);

		if (this.locationParam.getUserIdentity() != userValidatedByToken.getIdentity())
			throw new UserUnauthorizedException();
	}
}