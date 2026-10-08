package br.com.dfdevforge.sisfintransaction.statement.model.bank.services;

import br.com.dfdevforge.sisfintransaction.commons.services.BaseService;
import br.com.dfdevforge.sisfintransaction.statement.model.bank.entities.BankEntity;

public abstract class BankBaseService extends BaseService {
	protected BankEntity bankParam;

	public void setParams(BankEntity bankEntity) {
		this.bankParam = bankEntity;
	}
}