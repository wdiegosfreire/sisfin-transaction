package br.com.dfdevforge.sisfintransaction.statement.model.statementpattern.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.DataForEditionNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.statement.model.statementpattern.entities.StatementPatternEntity;
import br.com.dfdevforge.sisfintransaction.statement.model.statementpattern.repositories.StatementPatternRepository;

@Service
@RequestScope
@Transactional
public class StatementPatternExecuteEditionService extends StatementPatternBaseService implements CommonService {
	private final StatementPatternRepository statementPatternRepository;

	@Autowired
	public StatementPatternExecuteEditionService(StatementPatternRepository statementPatternRepository) {
		this.statementPatternRepository = statementPatternRepository;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findByIdentity();
		this.editStatementPattern();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("statementPatternRegistered", this.statementPatternParam);
		return super.returnBusinessData();
	}

	private void findByIdentity() throws DataForEditionNotFoundException {
		StatementPatternEntity statementPattern = this.statementPatternRepository.findByIdentity(this.statementPatternParam.getIdentity());

		if (statementPattern == null)
			throw new DataForEditionNotFoundException();
	}

	private void editStatementPattern() {
		this.statementPatternRepository.save(this.statementPatternParam);
	}
}