package br.com.dfdevforge.sisfintransaction.statement.service.statement;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.DataForEditionNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.statement.entities.StatementEntity;
import br.com.dfdevforge.sisfintransaction.statement.repositories.StatementItemRepository;
import br.com.dfdevforge.sisfintransaction.statement.repositories.StatementRepository;

@Service
public class StatementAccessEditionService extends StatementBaseService implements CommonService {
	@Autowired private StatementRepository statementRepository;
	@Autowired private StatementItemRepository statementItemRepository;

	private StatementEntity statementResult;

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findByIdentity();
		this.findItemsOfStatement();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("statement", this.statementResult);
		return super.returnBusinessData();
	}

	private void findByIdentity() throws DataForEditionNotFoundException {
		this.statementResult = this.statementRepository.findByIdentity(this.statementParam.getIdentity());

		if (statementResult == null)
			throw new DataForEditionNotFoundException();
	}

	private void findItemsOfStatement() {
		this.statementResult.setStatementItemList(statementItemRepository.findByStatement(this.statementParam));			
	}
}