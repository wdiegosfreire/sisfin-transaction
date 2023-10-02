package br.com.dfdevforge.sisfintransaction.transaction.services.account;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.DataForEditionNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.transaction.entities.AccountEntity;
import br.com.dfdevforge.sisfintransaction.transaction.repositories.AccountRepositoryCustomized;

@Service
public class AccountAccessRegistrationService extends AccountBaseService implements CommonService {
	@Autowired private AccountRepositoryCustomized accountRepositoryCustomized;

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findAccountsLevelOne();

		if (this.accountParam.getIdentity() != null)
			this.findAccountsLevelTwo();
	}

	private void findAccountsLevelOne() throws DataForEditionNotFoundException {
		List<AccountEntity> accountListComboLevelOne = this.accountRepositoryCustomized.searchAllWithLevel(1, this.accountParam.getUserIdentity());

		if (accountListComboLevelOne == null)
			throw new DataForEditionNotFoundException();

		this.setArtifact("accountListComboLevelOne", accountListComboLevelOne);
	}

	private void findAccountsLevelTwo() throws DataForEditionNotFoundException {
		List<AccountEntity> accountListComboLevelTwo = this.accountRepositoryCustomized.searchAllWithParent(this.accountParam.getIdentity(), this.accountParam.getUserIdentity());

		if (accountListComboLevelTwo == null)
			throw new DataForEditionNotFoundException();

		this.setArtifact("accountListComboLevelTwo", accountListComboLevelTwo);
	}
}