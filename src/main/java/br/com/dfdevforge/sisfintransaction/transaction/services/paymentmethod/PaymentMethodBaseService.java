package br.com.dfdevforge.sisfintransaction.transaction.services.paymentmethod;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.dfdevforge.sisfintransaction.commons.entities.UserEntity;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.UserUnauthorizedException;
import br.com.dfdevforge.sisfintransaction.commons.feignclients.UserFeignClient;
import br.com.dfdevforge.sisfintransaction.commons.services.BaseService;
import br.com.dfdevforge.sisfintransaction.transaction.entities.PaymentMethodEntity;
import feign.FeignException;

public abstract class PaymentMethodBaseService extends BaseService {
	protected PaymentMethodEntity paymentMethodParam;

	@Autowired private UserFeignClient userFeignClient;

	public void setParams(PaymentMethodEntity paymentMethodEntity, String token) {
		this.paymentMethodParam = paymentMethodEntity;
		this.token = token;
	}

	@Override
	public void validateUserAccess() throws BaseException {
		UserEntity userValidatedByToken = null;

		try {
			userValidatedByToken = this.userFeignClient.validateToken(this.token);

			if (this.paymentMethodParam.getUserIdentity() != userValidatedByToken.getIdentity())
				throw new UserUnauthorizedException();
		}
		catch (FeignException e) {
			throw new UserUnauthorizedException();
		}
	}
}