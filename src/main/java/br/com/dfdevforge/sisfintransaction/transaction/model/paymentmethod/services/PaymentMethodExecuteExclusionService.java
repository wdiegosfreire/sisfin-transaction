package br.com.dfdevforge.sisfintransaction.transaction.model.paymentmethod.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.DataForExclusionNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.transaction.model.paymentmethod.entities.PaymentMethodEntity;
import br.com.dfdevforge.sisfintransaction.transaction.model.paymentmethod.repositories.PaymentMethodRepository;

@Service
@RequestScope
@Transactional
public class PaymentMethodExecuteExclusionService extends PaymentMethodBaseService implements CommonService {
	private final PaymentMethodRepository paymentMethodRepository;

	@Autowired
	public PaymentMethodExecuteExclusionService(PaymentMethodRepository paymentMethodRepository) {
		this.paymentMethodRepository = paymentMethodRepository;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findById();
		this.deletePaymentMethod();
		this.findAllPaymentMethods();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("paymentMethodRegistered", this.paymentMethodParam);
		return super.returnBusinessData();
	}

	private void findById() throws DataForExclusionNotFoundException {
		PaymentMethodEntity paymentMethod = this.paymentMethodRepository.findByIdentity(this.paymentMethodParam.getIdentity());

		if (paymentMethod == null)
			throw new DataForExclusionNotFoundException();
	}

	private void deletePaymentMethod() {
		this.paymentMethodRepository.delete(this.paymentMethodParam);
	}

	private void findAllPaymentMethods() {
		this.setArtifact("paymentMethodList", this.paymentMethodRepository.findByUserIdentityOrderByNameAsc(paymentMethodParam.getUserIdentity()));
	}
}