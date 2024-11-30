package br.com.dfdevforge.sisfintransaction.transaction.model.account.services;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.dfdevforge.sisfintransaction.commons.entities.UserEntity;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.UserUnauthorizedException;
import br.com.dfdevforge.sisfintransaction.commons.feignclients.UserFeignClient;
import br.com.dfdevforge.sisfintransaction.commons.services.BaseService;
import br.com.dfdevforge.sisfintransaction.transaction.model.account.entities.AccountEntity;
import feign.FeignException;

public abstract class AccountBaseService extends BaseService {
	protected static final String ACCOUNT_LIST = "accountList";

	protected AccountEntity accountParam;

	@Autowired private UserFeignClient userFeignClient;

	public void setParams(AccountEntity accountEntity, String token) {
		this.accountParam = accountEntity;
		this.token = token;
	}

	@Override
	public void validateUserAccess() throws BaseException {
		UserEntity userValidatedByToken = null;

		try {
			userValidatedByToken = this.userFeignClient.validateToken(this.token);

			if (!this.accountParam.getUserIdentity().equals(userValidatedByToken.getIdentity()))
				throw new UserUnauthorizedException();
		}
		catch (FeignException e) {
			throw new UserUnauthorizedException();
		}
	}
}