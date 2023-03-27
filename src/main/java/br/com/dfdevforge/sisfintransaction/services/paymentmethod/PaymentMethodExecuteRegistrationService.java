package br.com.dfdevforge.sisfintransaction.services.paymentmethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.dfdevforge.common.exceptions.BaseException;
import br.com.dfdevforge.common.services.CommonService;
import br.com.dfdevforge.common.util.Utils;
import br.com.dfdevforge.sisfintransaction.exceptions.RequiredFieldNotFoundException;
import br.com.dfdevforge.sisfintransaction.repositories.PaymentMethodRepository;

@Service
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

		if (errorList != null && !errorList.isEmpty())
			throw new RequiredFieldNotFoundException("Required Field Not Found", errorList);
	}

	private void savePaymentMethod() throws BaseException {
		try {
			this.paymentMethodRepository.save(this.paymentMethodParam);
		}
		catch (DataIntegrityViolationException e) {
			throw new RequiredFieldNotFoundException();
		}
		catch (ConstraintViolationException e) {
			throw new RequiredFieldNotFoundException();
		}
		
	}

	private void findAllPaymentMethods() {
		this.setArtifact("paymentMethodList", this.paymentMethodRepository.findByUserIdentityOrderByNameAsc(paymentMethodParam.getUserIdentity()));
	}
}