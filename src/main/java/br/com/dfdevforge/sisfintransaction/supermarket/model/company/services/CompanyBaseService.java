package br.com.dfdevforge.sisfintransaction.supermarket.model.company.services;

import br.com.dfdevforge.sisfintransaction.commons.services.BaseService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.company.entities.CompanyEntity;

public abstract class CompanyBaseService extends BaseService {
	protected CompanyEntity companyParam;

	public void setParams(CompanyEntity companyEntity) {
		this.companyParam = companyEntity;
	}
}