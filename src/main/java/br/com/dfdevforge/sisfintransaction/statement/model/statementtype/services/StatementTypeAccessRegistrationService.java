package br.com.dfdevforge.sisfintransaction.statement.model.statementtype.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;

@Service
@RequestScope
@Transactional
public class StatementTypeAccessRegistrationService extends StatementTypeBaseService implements CommonService {

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findBanks();
		this.findAccountsSource();
	}

	private void findBanks() {
		this.setArtifact("bankListCombo", this.findBanksByUserIdentityOrderByNameAsc(this.statementTypeParam.getUserIdentity()));
	}

	private void findAccountsSource() {
		this.setArtifact("accountListComboSource", this.findAccountsByUserIdentityOrderByLevel(this.statementTypeParam.getUserIdentity()));
	}
}