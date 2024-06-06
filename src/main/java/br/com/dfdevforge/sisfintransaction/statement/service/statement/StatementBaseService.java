package br.com.dfdevforge.sisfintransaction.statement.service.statement;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.dfdevforge.sisfintransaction.commons.entities.UserEntity;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.UserUnauthorizedException;
import br.com.dfdevforge.sisfintransaction.commons.feignclients.UserFeignClient;
import br.com.dfdevforge.sisfintransaction.commons.services.BaseService;
import br.com.dfdevforge.sisfintransaction.statement.entities.StatementEntity;
import feign.FeignException;

public abstract class StatementBaseService extends BaseService {
	protected StatementEntity statementParam;

	@Autowired private UserFeignClient userFeignClient;

	public void setParams(StatementEntity statementEntity, String token) {
		this.statementParam = statementEntity;
		this.token = token;
	}

	@Override
	public void validateUserAccess() throws BaseException {
		UserEntity userValidatedByToken = null;

		try {
			userValidatedByToken = this.userFeignClient.validateToken(this.token);

			if (!this.statementParam.getUserIdentity().equals(userValidatedByToken.getIdentity()))
				throw new UserUnauthorizedException();
		}
		catch (FeignException e) {
			throw new UserUnauthorizedException();
		}
	}
}