package br.com.dfdevforge.sisfintransaction.statement.model.statement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.statement.model.statementtype.repositories.StatementTypeRepository;

@Service
@RequestScope
@Transactional
public class StatementAccessRegistrationService extends StatementBaseService implements CommonService {
	private final  StatementTypeRepository statementTypeRepository;

	@Autowired
	public StatementAccessRegistrationService(StatementTypeRepository statementTypeRepository) {
		this.statementTypeRepository = statementTypeRepository;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findBanks();

		if (this.statementParam.getStatementType() != null && this.statementParam.getStatementType().getBank() != null)
			this.findStatementTypes();
	}

	private void findBanks() {
		this.setArtifact("bankListCombo", this.findBanksByUserIdentityOrderByNameAsc(this.statementParam.getUserIdentity()));
	}

	private void findStatementTypes() {
		this.setArtifact("statementTypeListCombo", this.statementTypeRepository.findByUserIdentityAndBankOrderByNameAsc(
			this.statementParam.getUserIdentity(),
			this.statementParam.getStatementType().getBank()
		));
	}
}