package br.com.dfdevforge.sisfintransaction.supermarket.model.address.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.RequiredFieldNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.BaseService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.address.entities.AddressEntity;

public abstract class AddressBaseService extends BaseService {
	protected AddressEntity addressParam;

	public void setParams(AddressEntity addressEntity) {
		this.addressParam = addressEntity;
	}

	protected void checkRequiredFields() throws RequiredFieldNotFoundException {
		List<String> errorList = new ArrayList<>();

		if (StringUtils.isBlank(this.addressParam.getStreet()))
			errorList.add("Street is required.");
		if (StringUtils.isBlank(this.addressParam.getNumber()))
			errorList.add("Number is required.");
		if (StringUtils.isBlank(this.addressParam.getNeighborhood()))
			errorList.add("Neighborhood is required.");
		if (StringUtils.isBlank(this.addressParam.getZipCode()))
			errorList.add("ZipCode is required.");
		if (StringUtils.isBlank(this.addressParam.getCity()))
			errorList.add("City is required.");
		if (this.addressParam.getUserIdentity() == null)
			errorList.add("Please, the address need to be associated with a user.");

		if (!errorList.isEmpty())
			throw new RequiredFieldNotFoundException("Required Field Not Found", errorList);
	}
}