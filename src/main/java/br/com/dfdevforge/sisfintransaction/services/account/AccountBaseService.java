package br.com.dfdevforge.sisfintransaction.services.account;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.dfdevforge.common.exceptions.BaseException;
import br.com.dfdevforge.common.services.BaseService;
import br.com.dfdevforge.sisfintransaction.entities.AccountEntity;
import br.com.dfdevforge.sisfintransaction.entities.UserEntity;
import br.com.dfdevforge.sisfintransaction.exceptions.UserUnauthorizedException;
import br.com.dfdevforge.sisfintransaction.feignclients.UserFeignClient;

public abstract class AccountBaseService extends BaseService {
	protected AccountEntity accountParam;

	@Autowired private UserFeignClient userFeignClient;

	public void setParams(AccountEntity accountEntity, String token) {
		this.accountParam = accountEntity;
		this.token = token;
	}

	@Override
	public void validateUserAccess() throws BaseException {
		UserEntity userValidatedByToken = this.userFeignClient.validateToken(this.token);

		if (this.accountParam.getUserIdentity() != userValidatedByToken.getIdentity())
			throw new UserUnauthorizedException();
	}
}