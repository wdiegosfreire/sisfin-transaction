package br.com.dfdevforge.sisfintransaction.statement.service.statementpattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.statement.repositories.StatementPatternRepository;

@Service
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