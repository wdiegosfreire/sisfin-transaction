package br.com.dfdevforge.sisfintransaction.supermarket.model.address.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.address.repositories.AddressRepository;

@Service
@RequestScope
public class AddressExecuteRegistrationService extends AddressBaseService implements CommonService {
	private final AddressRepository addressRepository;

	@Autowired
	public AddressExecuteRegistrationService(AddressRepository addressRepository) {
		this.addressRepository = addressRepository;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.checkRequiredFields();
		this.saveAddress();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("addressRegistered", this.addressParam);
		return super.returnBusinessData();
	}

	private void saveAddress() {
		this.addressRepository.save(this.addressParam);
	}
}