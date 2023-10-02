package br.com.dfdevforge.sisfintransaction.statement.service.statement;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.DataForEditionNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.statement.entities.StatementEntity;
import br.com.dfdevforge.sisfintransaction.statement.repositories.StatementRepository;

@Service
public class StatementExecuteEditionService extends StatementBaseService implements CommonService {
	@Autowired private StatementRepository statementRepository;

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findByIdentity();
		this.editStatement();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("statementRegistered", this.statementParam);
		return super.returnBusinessData();
	}

	private void findByIdentity() throws DataForEditionNotFoundException {
		StatementEntity statement = this.statementRepository.findByIdentity(this.statementParam.getIdentity());

		if (statement == null)
			throw new DataForEditionNotFoundException();
	}

	private void editStatement() throws BaseException {
		this.statementRepository.save(this.statementParam);
	}
}