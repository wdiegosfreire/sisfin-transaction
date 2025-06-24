package br.com.dfdevforge.sisfintransaction.transaction.model.account.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.transaction.model.account.repositories.AccountRepository;
import br.com.dfdevforge.sisfintransaction.transaction.model.account.repositories.AccountRepositoryCustomized;

@Service
@RequestScope
@Transactional
public class AccountExecuteSearchService extends AccountBaseService implements CommonService {
	private final AccountRepository accountRepository;
	private final AccountRepositoryCustomized accountRepositoryCustomized;

	@Autowired
	public AccountExecuteSearchService(AccountRepository accountRepository, AccountRepositoryCustomized accountRepositoryCustomized) {
		this.accountRepository = accountRepository;
		this.accountRepositoryCustomized = accountRepositoryCustomized;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findAllAccounts();
	}

	private void findAllAccounts() {
		if (this.accountParam.getFilter() == null || this.accountParam.getFilter().contentEquals(""))
			this.setArtifact(ACCOUNT_LIST, this.accountRepository.findByUserIdentityOrderByLevel(this.accountParam.getUserIdentity()));
		else
			this.setArtifact(ACCOUNT_LIST, this.accountRepositoryCustomized.searchInAllProperties(this.accountParam));
	}
}