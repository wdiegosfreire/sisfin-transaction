package br.com.dfdevforge.sisfintransaction.transaction.services.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.transaction.repositories.AccountRepository;
import br.com.dfdevforge.sisfintransaction.transaction.repositories.AccountRepositoryCustomized;

@Service
@RequestScope
@Transactional
public class AccountExecuteSearchService extends AccountBaseService implements CommonService {
	@Autowired private AccountRepository accountRepository;
	@Autowired private AccountRepositoryCustomized accountRepositoryCustomized;

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