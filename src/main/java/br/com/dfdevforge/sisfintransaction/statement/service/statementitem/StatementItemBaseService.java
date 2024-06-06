package br.com.dfdevforge.sisfintransaction.statement.service.statementitem;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.dfdevforge.sisfintransaction.commons.entities.UserEntity;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.UserUnauthorizedException;
import br.com.dfdevforge.sisfintransaction.commons.feignclients.UserFeignClient;
import br.com.dfdevforge.sisfintransaction.commons.services.BaseService;
import br.com.dfdevforge.sisfintransaction.statement.entities.StatementItemEntity;
import feign.FeignException;

public abstract class StatementItemBaseService extends BaseService {
	protected StatementItemEntity statementItemParam;

	@Autowired private UserFeignClient userFeignClient;

	public void setParams(StatementItemEntity statementItemEntity, String token) {
		this.statementItemParam = statementItemEntity;
		this.token = token;
	}

	@Override
	public void validateUserAccess() throws BaseException {
		UserEntity userValidatedByToken = null;

		try {
			userValidatedByToken = this.userFeignClient.validateToken(this.token);

			if (this.statementItemParam.getUserIdentity() != userValidatedByToken.getIdentity())
				throw new UserUnauthorizedException();
		}
		catch (FeignException e) {
			throw new UserUnauthorizedException();
		}
	}
}