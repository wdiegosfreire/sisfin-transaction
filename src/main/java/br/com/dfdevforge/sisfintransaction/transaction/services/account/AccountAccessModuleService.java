package br.com.dfdevforge.sisfintransaction.transaction.services.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.transaction.repositories.AccountRepository;

@Service
@RequestScope
@Transactional
public class AccountAccessModuleService extends AccountBaseService implements CommonService {
	private final AccountRepository accountRepository;

	@Autowired
	public AccountAccessModuleService(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findAllAccounts();
	}

	private void findAllAccounts() {
		this.setArtifact(ACCOUNT_LIST, this.accountRepository.findByUserIdentityOrderByLevel(this.accountParam.getUserIdentity()));
	}
}