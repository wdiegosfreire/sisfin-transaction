package br.com.dfdevforge.sisfintransaction.statement.service.statementpattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.DataForEditionNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.statement.entities.StatementPatternEntity;
import br.com.dfdevforge.sisfintransaction.statement.repositories.StatementPatternRepository;

@Service
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
	}

	private void findByIdentity() throws DataForEditionNotFoundException {
		StatementPatternEntity statementPattern = this.statementPatternRepository.findByIdentity(this.statementPatternParam.getIdentity());

		if (statementPattern == null)
			throw new DataForEditionNotFoundException();

		this.setArtifact("statementPattern", statementPattern);
	}

	private void findLocations() {
		this.setArtifact("locationListCombo", this.findLocationsByUserIdentityOrderByNameAscBranchAsc(this.statementPatternParam.getUserIdentity()));
	}

	private void findAccountsTarget() {
		this.setArtifact("accountListComboTarget", this.findAccountsByUserIdentityOrderByLevel(this.statementPatternParam.getUserIdentity()));
	}
}