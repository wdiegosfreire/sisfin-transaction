package br.com.dfdevforge.sisfintransaction.services.objectivemovement;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.dfdevforge.common.exceptions.BaseException;
import br.com.dfdevforge.common.services.BaseService;
import br.com.dfdevforge.sisfintransaction.entities.ObjectiveMovementEntity;
import br.com.dfdevforge.sisfintransaction.entities.UserEntity;
import br.com.dfdevforge.sisfintransaction.exceptions.UserUnauthorizedException;
import br.com.dfdevforge.sisfintransaction.feignclients.UserFeignClient;
import feign.FeignException;

public abstract class ObjectiveMovementBaseService extends BaseService {
	protected ObjectiveMovementEntity objectiveMovementParam;

	@Autowired private UserFeignClient userFeignClient;

	public void setParams(ObjectiveMovementEntity objectiveMovementEntity, String token) {
		this.objectiveMovementParam = objectiveMovementEntity;
		this.token = token;
	}

	@Override
	public void validateUserAccess() throws BaseException {
		UserEntity userValidatedByToken = null;

		try {
			userValidatedByToken = this.userFeignClient.validateToken(this.token);

			if (this.objectiveMovementParam.getUserIdentity() != userValidatedByToken.getIdentity())
				throw new UserUnauthorizedException();
		}
		catch (FeignException e) {
			throw new UserUnauthorizedException();
		}
	}
}