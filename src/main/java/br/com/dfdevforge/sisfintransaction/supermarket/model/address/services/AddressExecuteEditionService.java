package br.com.dfdevforge.sisfintransaction.supermarket.model.address.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.DataForEditionNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.address.entities.AddressEntity;
import br.com.dfdevforge.sisfintransaction.supermarket.model.address.repositories.AddressRepository;

@Service
@RequestScope
public class AddressExecuteEditionService extends AddressBaseService implements CommonService {
	private final AddressRepository addressRepository;

	@Autowired
	public AddressExecuteEditionService(AddressRepository addressRepository) {
		this.addressRepository = addressRepository;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.checkRequiredFields();
		this.findById();
		this.editAddress();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("addressRegistered", this.addressParam);
		return super.returnBusinessData();
	}

	private void findById() throws DataForEditionNotFoundException {
		AddressEntity address = this.addressRepository.findByIdentity(this.addressParam.getIdentity());

		if (address == null || !this.addressParam.getUserIdentity().equals(address.getUserIdentity()))
			throw new DataForEditionNotFoundException();
	}

	private void editAddress() {
		this.addressParam = this.addressRepository.save(this.addressParam);
	}
}