package br.com.dfdevforge.sisfintransaction.transaction.model.summary.services;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.dfdevforge.sisfintransaction.commons.entities.UserEntity;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.UserUnauthorizedException;
import br.com.dfdevforge.sisfintransaction.commons.feignclients.UserFeignClient;
import br.com.dfdevforge.sisfintransaction.commons.services.BaseService;
import br.com.dfdevforge.sisfintransaction.transaction.model.summary.entities.SummaryEntity;
import feign.FeignException;

public abstract class SummaryBaseService extends BaseService {
	protected SummaryEntity summaryParam;

	@Autowired private UserFeignClient userFeignClient;

	public void setParams(SummaryEntity summaryEntity, String token) {
		this.summaryParam = summaryEntity;
		this.token = token;
	}

	@Override
	public void validateUserAccess() throws BaseException {
		UserEntity userValidatedByToken = null;

		try {
			userValidatedByToken = this.userFeignClient.validateToken(this.token);

			if (!this.summaryParam.getUserIdentity().equals(userValidatedByToken.getIdentity()))
				throw new UserUnauthorizedException();
		}
		catch (FeignException e) {
			throw new UserUnauthorizedException();
		}
	}
}