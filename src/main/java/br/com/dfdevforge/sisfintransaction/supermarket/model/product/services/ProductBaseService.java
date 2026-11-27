package br.com.dfdevforge.sisfintransaction.supermarket.model.product.services;

import br.com.dfdevforge.sisfintransaction.commons.services.BaseService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.product.entities.ProductEntity;

public abstract class ProductBaseService extends BaseService {
	protected ProductEntity productParam;

	public void setParams(ProductEntity productEntity) {
		this.productParam = productEntity;
	}
}