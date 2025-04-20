package br.com.dfdevforge.sisfintransaction.statement.model.statementpattern.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.DataForEditionNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.statement.model.statementpattern.entities.StatementPatternEntity;
import br.com.dfdevforge.sisfintransaction.statement.model.statementpattern.repositories.StatementPatternRepository;

@Service
@RequestScope
@Transactional
public class StatementPatternAccessEditionService extends StatementPatternBaseService implements CommonService {
	private final StatementPatternRepository statementPatternRepository;

	@Autowired
	public StatementPatternAccessEditionService(StatementPatternRepository statementPatternRepository) {
		this.statementPatternRepository = statementPatternRepository;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findByIdentity();
		this.findLocations();
		this.findAccountsTarget();
		this.findPaymentMethods();
		this.findStatementTypes();
	}

	private void findByIdentity() throws DataForEditionNotFoundException {
		StatementPatternEntity statementPattern = this.statementPatternRepository.findByIdentity(this.statementPatternParam.getIdentity());

		if (statementPattern == null)
			throw new DataForEditionNotFoundException();

		this.setArtifact("statementPattern", statementPattern);
	}

	private void findLocations() {
		this.setArtifact("locationListCombo", this.findLocationsByUserIdentityOrderByNameAsc(this.statementPatternParam.getUserIdentity()));
	}

	private void findAccountsTarget() {
		this.setArtifact("accountListComboTarget", this.findAccountsByUserIdentityOrderByLevel(this.statementPatternParam.getUserIdentity()));
	}

	private void findPaymentMethods() {
		this.setArtifact("paymentMethodListCombo", this.findPaymentMethodsByUserIdentityOrderByNameAsc(this.statementPatternParam.getUserIdentity()));
	}

	private void findStatementTypes() {
		this.setArtifact("statementTypeListCombo", this.findStatementTypesByUserIdentityOrderByNameAsc(this.statementPatternParam.getUserIdentity()));
	}
}