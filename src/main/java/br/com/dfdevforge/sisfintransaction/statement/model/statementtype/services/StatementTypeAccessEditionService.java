package br.com.dfdevforge.sisfintransaction.statement.model.statementtype.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.DataForEditionNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.statement.model.statementtype.entities.StatementTypeEntity;
import br.com.dfdevforge.sisfintransaction.statement.model.statementtype.repositories.StatementTypeRepository;

@Service
@RequestScope
@Transactional
public class StatementTypeAccessEditionService extends StatementTypeBaseService implements CommonService {
	private final StatementTypeRepository statementTypeRepository;

	@Autowired
	public StatementTypeAccessEditionService(StatementTypeRepository statementTypeRepository) {
		this.statementTypeRepository = statementTypeRepository;
	}

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

	private void findBanks() {
		this.setArtifact("bankListCombo", this.findBanksByUserIdentityOrderByNameAsc(this.statementTypeParam.getUserIdentity()));
	}

	private void findAccountsSource() {
		this.setArtifact("accountListComboSource", this.findAccountsByUserIdentityOrderByLevel(this.statementTypeParam.getUserIdentity()));
	}
}