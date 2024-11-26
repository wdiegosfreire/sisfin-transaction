package br.com.dfdevforge.sisfintransaction.statement.model.statementtype.services;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.dfdevforge.sisfintransaction.commons.entities.UserEntity;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.UserUnauthorizedException;
import br.com.dfdevforge.sisfintransaction.commons.feignclients.UserFeignClient;
import br.com.dfdevforge.sisfintransaction.commons.services.BaseService;
import br.com.dfdevforge.sisfintransaction.statement.model.statementtype.entities.StatementTypeEntity;
import feign.FeignException;

public abstract class StatementTypeBaseService extends BaseService {
	protected StatementTypeEntity statementTypeParam;

	@Autowired private UserFeignClient userFeignClient;

	public void setParams(StatementTypeEntity statementTypeEntity, String token) {
		this.statementTypeParam = statementTypeEntity;
		this.token = token;
	}

	@Override
	public void validateUserAccess() throws BaseException {
		UserEntity userValidatedByToken = null;

		try {
			userValidatedByToken = this.userFeignClient.validateToken(this.token);

			if (!this.statementTypeParam.getUserIdentity().equals(userValidatedByToken.getIdentity()))
				throw new UserUnauthorizedException();
		}
		catch (FeignException e) {
			throw new UserUnauthorizedException();
		}
	}
}