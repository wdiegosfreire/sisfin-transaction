package br.com.dfdevforge.sisfintransaction.statement.service.statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.DataForEditionNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.statement.repositories.StatementTypeRepository;

@Service
public class StatementAccessRegistrationService extends StatementBaseService implements CommonService {
	@Autowired private StatementTypeRepository statementTypeRepository;

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findBanks();

		if (this.statementParam.getStatementType() != null && this.statementParam.getStatementType().getBank() != null)
			this.findStatementTypes();
	}

	private void findBanks() throws DataForEditionNotFoundException {
		this.setArtifact("bankListCombo", this.findBanksByUserIdentityOrderByNameAsc(this.statementParam.getUserIdentity()));
	}

	private void findStatementTypes() throws DataForEditionNotFoundException {
		this.setArtifact("statementTypeListCombo", this.statementTypeRepository.findByUserIdentityAndBankOrderByNameAsc(
			this.statementParam.getUserIdentity(),
			this.statementParam.getStatementType().getBank()
		));
	}
}