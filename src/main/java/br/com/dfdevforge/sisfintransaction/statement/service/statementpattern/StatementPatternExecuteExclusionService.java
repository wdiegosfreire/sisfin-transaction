package br.com.dfdevforge.sisfintransaction.statement.service.statementpattern;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.DataForExclusionNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.statement.entities.StatementPatternEntity;
import br.com.dfdevforge.sisfintransaction.statement.repositories.StatementPatternRepository;

@Service
@RequestScope
@Transactional
public class StatementPatternExecuteExclusionService extends StatementPatternBaseService implements CommonService {
	private final StatementPatternRepository statementPatternRepository;

	@Autowired
	public StatementPatternExecuteExclusionService(StatementPatternRepository statementPatternRepository) {
		this.statementPatternRepository = statementPatternRepository;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findByIdentity();
		this.deleteStatementPattern();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("statementPatternRegistered", this.statementPatternParam);
		return super.returnBusinessData();
	}

	private void findByIdentity() throws DataForExclusionNotFoundException {
		StatementPatternEntity statementPattern = this.statementPatternRepository.findByIdentity(this.statementPatternParam.getIdentity());

		if (statementPattern == null)
			throw new DataForExclusionNotFoundException();
	}

	private void deleteStatementPattern() {
		this.statementPatternRepository.delete(this.statementPatternParam);
	}
}