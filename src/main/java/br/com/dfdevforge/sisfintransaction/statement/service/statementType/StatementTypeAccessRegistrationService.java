package br.com.dfdevforge.sisfintransaction.statement.service.statementType;

import org.springframework.stereotype.Service;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.DataForEditionNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;

@Service
public class StatementTypeAccessRegistrationService extends StatementTypeBaseService implements CommonService {

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findBanks();
		this.findAccountsSource();
	}

	private void findBanks() throws DataForEditionNotFoundException {
		this.setArtifact("bankListCombo", this.findBanksByUserIdentityOrderByNameAsc(this.statementTypeParam.getUserIdentity()));
	}

	private void findAccountsSource() {
		this.setArtifact("accountListComboSource", this.findAccountsByUserIdentityOrderByLevel(this.statementTypeParam.getUserIdentity()));
	}
}