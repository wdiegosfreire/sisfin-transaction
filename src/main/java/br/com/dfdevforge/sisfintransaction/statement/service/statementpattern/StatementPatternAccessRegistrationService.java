package br.com.dfdevforge.sisfintransaction.statement.service.statementpattern;

import org.springframework.stereotype.Service;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;

@Service
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