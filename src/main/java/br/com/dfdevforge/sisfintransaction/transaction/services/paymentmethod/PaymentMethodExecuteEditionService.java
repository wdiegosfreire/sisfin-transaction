package br.com.dfdevforge.sisfintransaction.transaction.services.paymentmethod;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.DataForEditionNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.transaction.entities.PaymentMethodEntity;
import br.com.dfdevforge.sisfintransaction.transaction.repositories.PaymentMethodRepository;

@Service
@RequestScope
@Transactional
public class PaymentMethodExecuteEditionService extends PaymentMethodBaseService implements CommonService {
	@Autowired private PaymentMethodRepository paymentMethodRepository;

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findById();
		this.editPaymentMethod();
		this.findAllPaymentMethods();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("paymentMethodRegistered", this.paymentMethodParam);
		return super.returnBusinessData();
	}

	private void findById() throws DataForEditionNotFoundException {
		PaymentMethodEntity paymentMethod = this.paymentMethodRepository.findByIdentity(this.paymentMethodParam.getIdentity());

		if (paymentMethod == null)
			throw new DataForEditionNotFoundException();
	}

	private void editPaymentMethod() {
		this.paymentMethodRepository.save(this.paymentMethodParam);
	}

	private void findAllPaymentMethods() {
		this.setArtifact("paymentMethodList", this.paymentMethodRepository.findByUserIdentityOrderByNameAsc(paymentMethodParam.getUserIdentity()));
	}
}