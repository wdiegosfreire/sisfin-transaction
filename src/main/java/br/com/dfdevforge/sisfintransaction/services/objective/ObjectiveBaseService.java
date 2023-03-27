package br.com.dfdevforge.sisfintransaction.services.objective;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.dfdevforge.common.exceptions.BaseException;
import br.com.dfdevforge.common.services.BaseService;
import br.com.dfdevforge.sisfintransaction.entities.ObjectiveEntity;
import br.com.dfdevforge.sisfintransaction.entities.UserEntity;
import br.com.dfdevforge.sisfintransaction.exceptions.UserUnauthorizedException;
import br.com.dfdevforge.sisfintransaction.feignclients.UserFeignClient;
import feign.FeignException;

public abstract class ObjectiveBaseService extends BaseService {
	protected static final String OBJECTIVE_LIST = "objectiveList";

	protected ObjectiveEntity objectiveParam;

	@Autowired private UserFeignClient userFeignClient;

	public void setParams(ObjectiveEntity objectiveEntity, String token) {
		this.objectiveParam = objectiveEntity;
		this.token = token;
	}

	@Override
	public void validateUserAccess() throws BaseException {
		UserEntity userValidatedByToken = null;

		try {
			userValidatedByToken = this.userFeignClient.validateToken(this.token);

			if (this.objectiveParam.getUserIdentity() != userValidatedByToken.getIdentity())
				throw new UserUnauthorizedException();
		}
		catch (FeignException e) {
			throw new UserUnauthorizedException();
		}
	}
}