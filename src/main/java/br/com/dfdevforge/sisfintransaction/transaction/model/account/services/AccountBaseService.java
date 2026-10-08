package br.com.dfdevforge.sisfintransaction.transaction.model.account.services;

import br.com.dfdevforge.sisfintransaction.commons.services.BaseService;
import br.com.dfdevforge.sisfintransaction.transaction.model.account.entities.AccountEntity;

public abstract class AccountBaseService extends BaseService {
	protected static final String ACCOUNT_LIST = "accountList";

	protected AccountEntity accountParam;

	public void setParams(AccountEntity accountEntity) {
		this.accountParam = accountEntity;
	}
}