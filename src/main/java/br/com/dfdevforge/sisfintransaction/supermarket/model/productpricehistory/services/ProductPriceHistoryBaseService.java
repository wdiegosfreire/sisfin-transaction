package br.com.dfdevforge.sisfintransaction.supermarket.model.productpricehistory.services;

import br.com.dfdevforge.sisfintransaction.commons.services.BaseService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.productpricehistory.entities.ProductPriceHistoryEntity;

public abstract class ProductPriceHistoryBaseService extends BaseService {
	protected ProductPriceHistoryEntity productPriceHistoryParam;

	public void setParams(ProductPriceHistoryEntity productPriceHistoryEntity) {
		this.productPriceHistoryParam = productPriceHistoryEntity;
	}
}