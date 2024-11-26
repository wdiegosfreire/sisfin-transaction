package br.com.dfdevforge.sisfintransaction.transaction.model.objectivemovement.services;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.dfdevforge.sisfintransaction.commons.entities.UserEntity;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.UserUnauthorizedException;
import br.com.dfdevforge.sisfintransaction.commons.feignclients.UserFeignClient;
import br.com.dfdevforge.sisfintransaction.commons.services.BaseService;
import br.com.dfdevforge.sisfintransaction.transaction.model.objectivemovement.entities.ObjectiveMovementEntity;
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

			if (!this.objectiveMovementParam.getUserIdentity().equals(userValidatedByToken.getIdentity()))
				throw new UserUnauthorizedException();
		}
		catch (FeignException e) {
			throw new UserUnauthorizedException();
		}
	}
}