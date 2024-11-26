package br.com.dfdevforge.sisfintransaction.transaction.model.paymentmethod.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.transaction.model.paymentmethod.repositories.PaymentMethodRepository;

@Service
@RequestScope
@Transactional
public class PaymentMethodAccessModuleService extends PaymentMethodBaseService implements CommonService {
	@Autowired private PaymentMethodRepository paymentMethodRepository;

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findAllLocations();
	}

	private void findAllLocations() {
		this.setArtifact("paymentMethodList", this.paymentMethodRepository.findByUserIdentityOrderByNameAsc(paymentMethodParam.getUserIdentity()));
	}
}