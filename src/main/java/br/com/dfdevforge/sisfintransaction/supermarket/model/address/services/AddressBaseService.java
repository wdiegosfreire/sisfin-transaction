package br.com.dfdevforge.sisfintransaction.supermarket.model.address.services;

import br.com.dfdevforge.sisfintransaction.commons.services.BaseService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.address.entities.AddressEntity;

public abstract class AddressBaseService extends BaseService {
	protected AddressEntity addressParam;

	public void setParams(AddressEntity addressEntity) {
		this.addressParam = addressEntity;
	}
}