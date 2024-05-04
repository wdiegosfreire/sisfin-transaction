package br.com.dfdevforge.sisfintransaction.statement.service.statementpattern;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.DataForEditionNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.statement.entities.StatementPatternEntity;
import br.com.dfdevforge.sisfintransaction.statement.repositories.StatementPatternRepository;

@Service
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