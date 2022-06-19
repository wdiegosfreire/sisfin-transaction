package br.com.dfdevforge.sisfintransaction.services.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dfdevforge.common.exceptions.BaseException;
import br.com.dfdevforge.common.services.CommonService;
import br.com.dfdevforge.sisfintransaction.repositories.AccountRepository;
import br.com.dfdevforge.sisfintransaction.repositories.AccountRepositoryCustomized;

@Service
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