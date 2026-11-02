package br.com.dfdevforge.sisfintransaction.supermarket.model.productproperty.services;

import br.com.dfdevforge.sisfintransaction.commons.services.BaseService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.productproperty.entities.ProductPropertyEntity;

public abstract class ProductPropertyBaseService extends BaseService {
	protected ProductPropertyEntity productPropertyParam;

	public void setParams(ProductPropertyEntity productPropertyEntity) {
		this.productPropertyParam = productPropertyEntity;
	}
}