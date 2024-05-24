package br.com.dfdevforge.sisfintransaction.statement.service.statementpattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.statement.repositories.StatementPatternRepository;
import br.com.dfdevforge.sisfintransaction.statement.repositories.StatementPatternRepositoryCustomized;

@Service
@RequestScope
@Transactional
public class StatementPatternExecuteSearchService extends StatementPatternBaseService implements CommonService {
	private final StatementPatternRepository statementPatternRepository;
	private final StatementPatternRepositoryCustomized statementPatternRepositoryCustomized;

	@Autowired
	public StatementPatternExecuteSearchService(StatementPatternRepository statementPatternRepository, StatementPatternRepositoryCustomized statementPatternRepositoryCustomized) {
		this.statementPatternRepository = statementPatternRepository;
		this.statementPatternRepositoryCustomized = statementPatternRepositoryCustomized;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findAllStatementPatterns();
	}

	private void findAllStatementPatterns() {
		if (this.statementPatternParam.getFilter() == null || this.statementPatternParam.getFilter().contentEquals(""))
			this.setArtifact("statementPatternList", this.statementPatternRepository
					.findByUserIdentityOrderByComparatorAsc(statementPatternParam.getUserIdentity()));
		else
			this.setArtifact("statementPatternList",
					this.statementPatternRepositoryCustomized.searchInAllProperties(this.statementPatternParam));
	}
}