package br.com.dfdevforge.sisfintransaction.transaction.model.paymentmethod.services;

import br.com.dfdevforge.sisfintransaction.commons.services.BaseService;
import br.com.dfdevforge.sisfintransaction.transaction.model.paymentmethod.entities.PaymentMethodEntity;

public abstract class PaymentMethodBaseService extends BaseService {
	protected PaymentMethodEntity paymentMethodParam;

	public void setParams(PaymentMethodEntity paymentMethodEntity) {
		this.paymentMethodParam = paymentMethodEntity;
	}
}