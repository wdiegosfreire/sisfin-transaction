package br.com.dfdevforge.sisfintransaction.services.paymentmethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dfdevforge.common.exceptions.BaseException;
import br.com.dfdevforge.common.services.CommonService;
import br.com.dfdevforge.sisfintransaction.repositories.PaymentMethodRepository;

@Service
public class PaymentMethodAccessModuleService extends PaymentMethodBaseService implements CommonService {
	@Autowired private PaymentMethodRepository paymentMethodRepository;

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findAllLocations();
	}

	private void findAllLocations() {
		this.setArtifact("paymentMethodList", this.paymentMethodRepository.findByUserIdentity(paymentMethodParam.getUserIdentity()));
	}
}