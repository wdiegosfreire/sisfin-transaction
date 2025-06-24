package br.com.dfdevforge.sisfintransaction.statement.model.statement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.statement.model.statement.repositories.StatementRepository;
import br.com.dfdevforge.sisfintransaction.statement.model.statement.repositories.StatementRepositoryCustomized;

@Service
@RequestScope
@Transactional
public class StatementExecuteSearchService extends StatementBaseService implements CommonService {
	private final StatementRepository statementRepository;
	private final StatementRepositoryCustomized statementRepositoryCustomized;

	@Autowired
	public StatementExecuteSearchService(StatementRepository statementRepository, StatementRepositoryCustomized statementRepositoryCustomized) {
		this.statementRepository = statementRepository;
		this.statementRepositoryCustomized = statementRepositoryCustomized;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findAllStatements();
	}

	private void findAllStatements() {
		if (this.statementParam.getFilter() == null || this.statementParam.getFilter().contentEquals(""))
			this.setArtifact("statementList", this.statementRepository.findByUserIdentityOrderByYearAscMonthAsc(statementParam.getUserIdentity()));
		else
			this.setArtifact("statementList", this.statementRepositoryCustomized.searchInAllProperties(this.statementParam));
	}
}