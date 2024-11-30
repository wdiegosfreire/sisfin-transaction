package br.com.dfdevforge.sisfintransaction.transaction.model.paymentmethod.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.commons.utils.Utils;
import br.com.dfdevforge.sisfintransaction.transaction.model.paymentmethod.repositories.PaymentMethodRepository;
import br.com.dfdevforge.sisfintransaction.transaction.model.paymentmethod.repositories.PaymentMethodRepositoryCustomized;

@Service
@RequestScope
@Transactional
public class PaymentMethodExecuteSearchService extends PaymentMethodBaseService implements CommonService {
	@Autowired private PaymentMethodRepository paymentMethodRepository;
	@Autowired private PaymentMethodRepositoryCustomized paymentMethodRepositoryCustomized;

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findAllPaymentMethods();
	}

	private void findAllPaymentMethods() {
		if (Utils.value.notExists(this.paymentMethodParam.getFilter()))
			this.setArtifact("paymentMethodList", this.paymentMethodRepository.findByUserIdentityOrderByNameAsc(paymentMethodParam.getUserIdentity()));
		else
			this.setArtifact("paymentMethodList", this.paymentMethodRepositoryCustomized.searchInAllProperties(this.paymentMethodParam));
	}
}