package br.com.dfdevforge.sisfintransaction.transaction.services.paymentmethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.DataForEditionNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.transaction.entities.PaymentMethodEntity;
import br.com.dfdevforge.sisfintransaction.transaction.repositories.PaymentMethodRepository;

@Service
public class PaymentMethodAccessEditionService extends PaymentMethodBaseService implements CommonService {
	@Autowired private PaymentMethodRepository paymentMethodRepository;

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findById();
	}

	private void findById() throws DataForEditionNotFoundException {
		PaymentMethodEntity paymentMethod = this.paymentMethodRepository.findByIdentity(this.paymentMethodParam.getIdentity());

		if (paymentMethod == null)
			throw new DataForEditionNotFoundException();

		this.setArtifact("paymentMethod", paymentMethod);
	}
}