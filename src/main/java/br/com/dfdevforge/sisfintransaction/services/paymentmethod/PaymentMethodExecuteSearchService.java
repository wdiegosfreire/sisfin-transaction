package br.com.dfdevforge.sisfintransaction.services.paymentmethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dfdevforge.common.exceptions.BaseException;
import br.com.dfdevforge.common.services.CommonService;
import br.com.dfdevforge.common.util.Utils;
import br.com.dfdevforge.sisfintransaction.repositories.PaymentMethodRepository;
import br.com.dfdevforge.sisfintransaction.repositories.PaymentMethodRepositoryCustomized;

@Service
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