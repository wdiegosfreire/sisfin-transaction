package br.com.dfdevforge.sisfintransaction.services.account;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dfdevforge.common.exceptions.BaseException;
import br.com.dfdevforge.common.services.CommonService;
import br.com.dfdevforge.sisfintransaction.entities.AccountEntity;
import br.com.dfdevforge.sisfintransaction.exceptions.DataForEditionNotFoundException;
import br.com.dfdevforge.sisfintransaction.repositories.AccountRepositoryCustomized;

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
		List<AccountEntity> accountListComboLevelOne = this.accountRepositoryCustomized.searchAllWithLevel(1);

		if (accountListComboLevelOne == null)
			throw new DataForEditionNotFoundException();

		this.setArtifact("accountListComboLevelOne", accountListComboLevelOne);
	}

	private void findAccountsLevelTwo() throws DataForEditionNotFoundException {
		List<AccountEntity> accountListComboLevelTwo = this.accountRepositoryCustomized.searchAllWithParent(this.accountParam.getIdentity());

		if (accountListComboLevelTwo == null)
			throw new DataForEditionNotFoundException();

		this.setArtifact("accountListComboLevelTwo", accountListComboLevelTwo);
	}
}