package br.com.dfdevforge.sisfintransaction.services.paymentmethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dfdevforge.common.exceptions.BaseException;
import br.com.dfdevforge.common.services.CommonService;
import br.com.dfdevforge.sisfintransaction.entities.PaymentMethodEntity;
import br.com.dfdevforge.sisfintransaction.exceptions.DataForEditionNotFoundException;
import br.com.dfdevforge.sisfintransaction.repositories.PaymentMethodRepository;

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