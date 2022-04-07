package br.com.dfdevforge.sisfintransaction.services.paymentmethod;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.dfdevforge.common.exceptions.BaseException;
import br.com.dfdevforge.common.services.BaseService;
import br.com.dfdevforge.sisfintransaction.entities.PaymentMethodEntity;
import br.com.dfdevforge.sisfintransaction.entities.UserEntity;
import br.com.dfdevforge.sisfintransaction.exceptions.UserUnauthorizedException;
import br.com.dfdevforge.sisfintransaction.feignclients.UserFeignClient;

public abstract class PaymentMethodBaseService extends BaseService {
	protected PaymentMethodEntity paymentMethodParam;

	@Autowired private UserFeignClient userFeignClient;

	public void setParams(PaymentMethodEntity paymentMethodEntity, String token) {
		this.paymentMethodParam = paymentMethodEntity;
		this.token = token;
	}

	@Override
	public void validateUserAccess() throws BaseException {
		UserEntity userValidatedByToken = this.userFeignClient.validateToken(this.token);

		if (this.paymentMethodParam.getUserIdentity() != userValidatedByToken.getIdentity())
			throw new UserUnauthorizedException();
	}
}