package br.com.dfdevforge.sisfintransaction.supermarket.model.productcategory.services;

import br.com.dfdevforge.sisfintransaction.commons.services.BaseService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.productcategory.entities.ProductCategoryEntity;

public abstract class ProductCategoryBaseService extends BaseService {
	protected ProductCategoryEntity productCategoryParam;

	public void setParams(ProductCategoryEntity productCategoryEntity) {
		this.productCategoryParam = productCategoryEntity;
	}
}