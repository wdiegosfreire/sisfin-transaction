package br.com.dfdevforge.sisfintransaction.statement.model.statementtype.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.statement.model.statementtype.repositories.StatementTypeRepository;
import br.com.dfdevforge.sisfintransaction.statement.model.statementtype.repositories.StatementTypeRepositoryCustomized;

@Service
@RequestScope
@Transactional
public class StatementTypeExecuteSearchService extends StatementTypeBaseService implements CommonService {
	private final StatementTypeRepository statementTypeRepository;
	private final StatementTypeRepositoryCustomized statementTypeRepositoryCustomized;

	@Autowired
	public StatementTypeExecuteSearchService(StatementTypeRepository statementTypeRepository, StatementTypeRepositoryCustomized statementTypeRepositoryCustomized) {
		this.statementTypeRepository = statementTypeRepository;
		this.statementTypeRepositoryCustomized = statementTypeRepositoryCustomized;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findAllStatementTypes();
	}

	private void findAllStatementTypes() {
		if (this.statementTypeParam.getFilter() == null || this.statementTypeParam.getFilter().contentEquals(""))
			this.setArtifact("statementTypeList", this.statementTypeRepository.findByUserIdentityOrderByNameAsc(statementTypeParam.getUserIdentity()));
		else
			this.setArtifact("statementTypeList", this.statementTypeRepositoryCustomized.searchInAllProperties(this.statementTypeParam));
	}
}