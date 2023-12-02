package br.com.dfdevforge.sisfintransaction.statement.service.statementType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.DataForEditionNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.statement.entities.StatementTypeEntity;
import br.com.dfdevforge.sisfintransaction.statement.repositories.StatementTypeRepository;

@Service
public class StatementTypeAccessEditionService extends StatementTypeService implements CommonService {
	@Autowired private StatementTypeRepository statementTypeRepository;

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findByIdentity();
		this.findBanks();
		this.findAccountsSource();
	}

	private void findByIdentity() throws DataForEditionNotFoundException {
		StatementTypeEntity statementType = this.statementTypeRepository.findByIdentity(this.statementTypeParam.getIdentity());

		if (statementType == null)
			throw new DataForEditionNotFoundException();

		this.setArtifact("statementType", statementType);
	}

	private void findBanks() throws DataForEditionNotFoundException {
		this.setArtifact("bankListCombo", this.findBanksByUserIdentityOrderByNameAsc(this.statementTypeParam.getUserIdentity()));
	}

	private void findAccountsSource() {
		this.setArtifact("accountListComboSource", this.findAccountsByUserIdentityOrderByLevel(this.statementTypeParam.getUserIdentity()));
	}
}