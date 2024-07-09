package br.com.dfdevforge.sisfintransaction.transaction.services.account;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.transaction.entities.AccountEntity;
import br.com.dfdevforge.sisfintransaction.transaction.repositories.account.AccountRepositoryCustomized;

@Service
@RequestScope
@Transactional
public class AccountAccessModuleService extends AccountBaseService implements CommonService {
	private final AccountRepositoryCustomized accountRepositoryCustomized;

	@Autowired
	public AccountAccessModuleService(AccountRepositoryCustomized accountRepositoryCustomized) {
		this.accountRepositoryCustomized = accountRepositoryCustomized;
	}

	private List<AccountEntity> accountListResult;

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findAllAccounts();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact(ACCOUNT_LIST, this.accountListResult);
		return super.returnBusinessData();
	}

	private void findAllAccounts() {
		this.accountListResult = this.accountRepositoryCustomized.searchAllWithParent(this.accountParam.getIdentity(), this.accountParam.getUserIdentity());

		this.accountListResult.forEach(accountLevelOne -> {
			accountLevelOne.setAccountListChild(this.accountRepositoryCustomized.searchAllWithParent(accountLevelOne.getIdentity(), this.accountParam.getUserIdentity()));

			accountLevelOne.getAccountListChild().forEach(accountLevelTwo -> {
				accountLevelTwo.setAccountParent(null);
				accountLevelTwo.setAccountListChild(this.accountRepositoryCustomized.searchAllWithParent(accountLevelTwo.getIdentity(), this.accountParam.getUserIdentity()));

				accountLevelTwo.getAccountListChild().forEach(accountLevelThree -> accountLevelThree.setAccountParent(null));
			});
		});
	}
}