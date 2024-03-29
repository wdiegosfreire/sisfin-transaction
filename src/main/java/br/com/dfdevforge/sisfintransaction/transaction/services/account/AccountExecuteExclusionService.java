package br.com.dfdevforge.sisfintransaction.transaction.services.account;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.ChildrenInformationFoundException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.DataForExclusionNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.transaction.entities.AccountEntity;
import br.com.dfdevforge.sisfintransaction.transaction.repositories.AccountRepository;
import br.com.dfdevforge.sisfintransaction.transaction.repositories.AccountRepositoryCustomized;

@Service
public class AccountExecuteExclusionService extends AccountBaseService implements CommonService {
	@Autowired private AccountRepository accountRepository;
	@Autowired private AccountRepositoryCustomized accountRepositoryCustomized;

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findById();
		this.checkIfAccountHaveChildren();
		this.deleteAccount();
		this.findAllAccounts();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("accountRegistered", this.accountParam);
		return super.returnBusinessData();
	}

	private void findById() throws DataForExclusionNotFoundException {
		AccountEntity account = this.accountRepository.findByIdentity(this.accountParam.getIdentity());

		if (account == null)
			throw new DataForExclusionNotFoundException();
	}

	private void checkIfAccountHaveChildren() throws ChildrenInformationFoundException {
		List<AccountEntity> accountList = this.accountRepositoryCustomized.searchAllWithParent(this.accountParam.getIdentity(), this.accountParam.getUserIdentity());

		if (accountList != null && !accountList.isEmpty())
			throw new ChildrenInformationFoundException();
	}

	private void deleteAccount() throws BaseException {
		this.accountRepository.delete(this.accountParam);
	}

	private void findAllAccounts() {
		this.setArtifact(ACCOUNT_LIST, this.accountRepository.findByUserIdentityOrderByLevel(this.accountParam.getUserIdentity()));
	}
}