package br.com.dfdevforge.sisfintransaction.services.account;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dfdevforge.common.exceptions.BaseException;
import br.com.dfdevforge.common.services.CommonService;
import br.com.dfdevforge.sisfintransaction.entities.AccountEntity;
import br.com.dfdevforge.sisfintransaction.exceptions.ChildrenInformationFoundException;
import br.com.dfdevforge.sisfintransaction.exceptions.DataForExclusionNotFoundException;
import br.com.dfdevforge.sisfintransaction.repositories.AccountRepository;
import br.com.dfdevforge.sisfintransaction.repositories.AccountRepositoryCustomized;

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
		List<AccountEntity> accountList = this.accountRepositoryCustomized.searchAllWithParent(this.accountParam.getIdentity());

		if (accountList != null && !accountList.isEmpty())
			throw new ChildrenInformationFoundException();
	}

	private void deleteAccount() throws BaseException {
		this.accountRepository.delete(this.accountParam);
	}

	private void findAllAccounts() {
		this.setArtifact("accountList", this.accountRepository.findByUserIdentityOrderByLevel(this.accountParam.getUserIdentity()));
	}
}