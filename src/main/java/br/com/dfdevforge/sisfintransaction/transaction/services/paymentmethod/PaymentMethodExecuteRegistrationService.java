package br.com.dfdevforge.sisfintransaction.transaction.services.paymentmethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.RequiredFieldNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.commons.utils.Utils;
import br.com.dfdevforge.sisfintransaction.transaction.repositories.PaymentMethodRepository;

@Service
@RequestScope
@Transactional
public class PaymentMethodExecuteRegistrationService extends PaymentMethodBaseService implements CommonService {
	@Autowired private PaymentMethodRepository paymentMethodRepository;

	@Override
	public void executeBusinessRule() throws BaseException {
		this.checkRequiredFields();
		this.savePaymentMethod();
		this.findAllPaymentMethods();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("paymentMethodRegistered", this.paymentMethodParam);
		return super.returnBusinessData();
	}

	private void checkRequiredFields() throws RequiredFieldNotFoundException {
		List<String> errorList = new ArrayList<>();

		if (Utils.value.notExists(this.paymentMethodParam.getName()))
			errorList.add("Please, enter the name.");
		if (Utils.value.notExists(this.paymentMethodParam.getAcronym()))
			errorList.add("Please, enter the acronym.");
		if (this.paymentMethodParam.getUserIdentity() == null)
			errorList.add("Please, the payment method need to be associated with a user.");

		if (!errorList.isEmpty())
			throw new RequiredFieldNotFoundException("Required Field Not Found", errorList);
	}

	private void savePaymentMethod() throws BaseException {
		try {
			this.paymentMethodRepository.save(this.paymentMethodParam);
		}
		catch (DataIntegrityViolationException | ConstraintViolationException e) {
			throw new RequiredFieldNotFoundException();
		}
	}

	private void findAllPaymentMethods() {
		this.setArtifact("paymentMethodList", this.paymentMethodRepository.findByUserIdentityOrderByNameAsc(paymentMethodParam.getUserIdentity()));
	}
}