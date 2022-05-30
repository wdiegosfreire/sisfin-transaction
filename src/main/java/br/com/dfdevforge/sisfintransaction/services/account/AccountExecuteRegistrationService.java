package br.com.dfdevforge.sisfintransaction.services.account;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dfdevforge.common.exceptions.BaseException;
import br.com.dfdevforge.common.services.CommonService;
import br.com.dfdevforge.sisfintransaction.exceptions.RequiredFieldNotFoundException;
import br.com.dfdevforge.sisfintransaction.repositories.AccountRepository;

@Service
public class AccountExecuteRegistrationService extends AccountBaseService implements CommonService {
	@Autowired private AccountRepository accountRepository;

	@Override
	public void executeBusinessRule() throws BaseException {
		this.checkRequiredFields();
		this.setDefaultValues();
		this.saveAccount();
		this.findAllAccounts();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("accountRegistered", this.accountParam);
		return super.returnBusinessData();
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

		if (errorList != null && !errorList.isEmpty())
			throw new RequiredFieldNotFoundException("Required Field Not Found", errorList);
	}

	private void setDefaultValues() {
		this.accountParam.setIsInactive(Boolean.FALSE);

		if (this.accountParam.getIcon() == null || this.accountParam.getIcon().trim().equals(""))
			this.accountParam.setIcon("fa-font-awesome");
	}

	private void saveAccount() throws BaseException {
		this.accountRepository.save(this.accountParam);
	}

	private void findAllAccounts() {
		this.setArtifact("accountList", this.accountRepository.findByUserIdentityOrderByLevel(this.accountParam.getUserIdentity()));
	}
}