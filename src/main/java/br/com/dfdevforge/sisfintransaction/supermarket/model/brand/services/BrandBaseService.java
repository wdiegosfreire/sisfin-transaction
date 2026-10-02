package br.com.dfdevforge.sisfintransaction.supermarket.model.brand.services;

import br.com.dfdevforge.sisfintransaction.commons.services.BaseService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.brand.entities.BrandEntity;

public abstract class BrandBaseService extends BaseService {
	protected BrandEntity brandParam;

	public void setParams(BrandEntity brandEntity) {
		this.brandParam = brandEntity;
	}
}