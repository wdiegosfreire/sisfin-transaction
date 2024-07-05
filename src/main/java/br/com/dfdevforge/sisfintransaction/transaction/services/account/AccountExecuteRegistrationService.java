package br.com.dfdevforge.sisfintransaction.transaction.services.account;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.RequiredFieldNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.transaction.entities.AccountEntity;
import br.com.dfdevforge.sisfintransaction.transaction.repositories.account.AccountRepository;
import br.com.dfdevforge.sisfintransaction.transaction.repositories.account.AccountRepositoryCustomized;

@Service
@RequestScope
@Transactional
public class AccountExecuteRegistrationService extends AccountBaseService implements CommonService {
	private final AccountRepository accountRepository;
	private final AccountRepositoryCustomized accountRepositoryCustomized;

	@Autowired
	public AccountExecuteRegistrationService(AccountRepository accountRepository, AccountRepositoryCustomized accountRepositoryCustomized) {
		this.accountRepository = accountRepository;
		this.accountRepositoryCustomized = accountRepositoryCustomized;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.calculateAccountLevel();
		this.checkRequiredFields();
		this.setDefaultValues();
		this.saveAccount();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("accountRegistered", this.accountParam);
		return super.returnBusinessData();
	}

	private void calculateAccountLevel() {
		List<AccountEntity> accountList = this.accountRepositoryCustomized.searchAllWithParent(
			this.accountParam.getAccountParent().getIdentity(),
			this.accountParam.getUserIdentity()
		);

		int newLevel = 1;
		if (accountList != null && !accountList.isEmpty())
			newLevel = accountList.size() + 1;

		this.accountParam.setLevel(this.accountParam.getAccountParent().getLevel() + (newLevel < 10 ? "0" : "") + newLevel + ".");
	}

	private void checkRequiredFields() throws RequiredFieldNotFoundException {
		List<String> errorList = new ArrayList<>();

		if (this.accountParam.getName() == null || this.accountParam.getName().equals(""))
			errorList.add("Please, enter the name.");
		if (this.accountParam.getLevel() == null || this.accountParam.getLevel().equals(""))
			errorList.add("Please, enter the level.");
		if (this.accountParam.getAccountParent() == null || this.accountParam.getAccountParent().getIdentity() == null)
			errorList.add("Please, enter the parent account.");
		if (this.accountParam.getUserIdentity() == null)
			errorList.add("Please, the account need to be associated with a user.");

		if (!errorList.isEmpty())
			throw new RequiredFieldNotFoundException("Required Field Not Found", errorList);
	}

	private void setDefaultValues() {
		this.accountParam.setIsInactive(Boolean.FALSE);

		if (this.accountParam.getIcon() == null || this.accountParam.getIcon().trim().equals(""))
			this.accountParam.setIcon("fa-font-awesome");
	}

	private void saveAccount() {
		this.accountRepository.save(this.accountParam);
	}
}