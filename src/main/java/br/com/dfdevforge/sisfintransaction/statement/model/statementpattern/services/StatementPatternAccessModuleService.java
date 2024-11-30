package br.com.dfdevforge.sisfintransaction.statement.model.statementpattern.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.statement.model.statementpattern.repositories.StatementPatternRepository;

@Service
@RequestScope
@Transactional
public class StatementPatternAccessModuleService extends StatementPatternBaseService implements CommonService {
	private final StatementPatternRepository statementPatternRepository;

	@Autowired
	public StatementPatternAccessModuleService(StatementPatternRepository statementPatternRepository) {
		this.statementPatternRepository = statementPatternRepository;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findAllStatementPatterns();
	}

	private void findAllStatementPatterns() {
		this.setArtifact("statementPatternList", this.statementPatternRepository.findByUserIdentityOrderByComparatorAsc(statementPatternParam.getUserIdentity()));
	}
}