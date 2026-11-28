package br.com.dfdevforge.sisfintransaction.supermarket.model.productcompany.services;

import br.com.dfdevforge.sisfintransaction.commons.services.BaseService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.productcompany.entities.ProductCompanyEntity;

public abstract class ProductCompanyBaseService extends BaseService {
	protected ProductCompanyEntity productCompanyParam;

	public void setParams(ProductCompanyEntity productCompanyEntity) {
		this.productCompanyParam = productCompanyEntity;
	}
}