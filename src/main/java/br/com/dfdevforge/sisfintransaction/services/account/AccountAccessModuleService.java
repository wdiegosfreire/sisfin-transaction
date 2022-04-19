package br.com.dfdevforge.sisfintransaction.services.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dfdevforge.common.exceptions.BaseException;
import br.com.dfdevforge.common.services.CommonService;
import br.com.dfdevforge.sisfintransaction.repositories.AccountRepository;

@Service
public class AccountAccessModuleService extends AccountBaseService implements CommonService {
	@Autowired private AccountRepository accountRepository;

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findAllAccounts();
	}

	private void findAllAccounts() {
		this.setArtifact("accountList", this.accountRepository.findByUserIdentity(accountParam.getUserIdentity()));
	}
}