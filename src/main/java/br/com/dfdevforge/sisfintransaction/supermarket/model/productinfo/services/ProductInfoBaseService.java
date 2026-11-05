package br.com.dfdevforge.sisfintransaction.supermarket.model.productinfo.services;

import br.com.dfdevforge.sisfintransaction.commons.services.BaseService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.productinfo.entities.ProductInfoEntity;

public abstract class ProductInfoBaseService extends BaseService {
	protected ProductInfoEntity productInfoParam;

	public void setParams(ProductInfoEntity productInfoEntity) {
		this.productInfoParam = productInfoEntity;
	}
}