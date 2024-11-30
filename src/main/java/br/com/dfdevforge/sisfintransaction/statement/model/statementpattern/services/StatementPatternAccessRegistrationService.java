package br.com.dfdevforge.sisfintransaction.statement.model.statementpattern.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;

@Service
@RequestScope
@Transactional
public class StatementPatternAccessRegistrationService extends StatementPatternBaseService implements CommonService {

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findLocations();
		this.findAccountsTarget();
	}

	private void findLocations() {
		this.setArtifact("locationListCombo", this.findLocationsByUserIdentityOrderByNameAsc(this.statementPatternParam.getUserIdentity()));
	}

	private void findAccountsTarget() {
		this.setArtifact("accountListComboTarget", this.findAccountsByUserIdentityOrderByLevel(this.statementPatternParam.getUserIdentity()));
	}
}