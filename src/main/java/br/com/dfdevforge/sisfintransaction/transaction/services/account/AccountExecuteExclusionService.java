package br.com.dfdevforge.sisfintransaction.transaction.services.account;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.ChildrenInformationFoundException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.DataForExclusionNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.ForeignKeyConstraintException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.transaction.entities.AccountEntity;
import br.com.dfdevforge.sisfintransaction.transaction.repositories.account.AccountRepository;
import br.com.dfdevforge.sisfintransaction.transaction.repositories.account.AccountRepositoryCustomized;

@Service
@RequestScope
@Transactional
public class AccountExecuteExclusionService extends AccountBaseService implements CommonService {
	private final AccountRepository accountRepository;
	private final AccountRepositoryCustomized accountRepositoryCustomized;

	@Autowired
	public AccountExecuteExclusionService(AccountRepository accountRepository, AccountRepositoryCustomized accountRepositoryCustomized) {
		this.accountRepository = accountRepository;
		this.accountRepositoryCustomized = accountRepositoryCustomized;
	}

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
		try {
			this.accountRepository.delete(this.accountParam);
		}
		catch (DataIntegrityViolationException e) {
			throw new ForeignKeyConstraintException();
		}
	}

	private void findAllAccounts() {
		this.setArtifact(ACCOUNT_LIST, this.accountRepository.findByUserIdentityOrderByLevel(this.accountParam.getUserIdentity()));
	}
}