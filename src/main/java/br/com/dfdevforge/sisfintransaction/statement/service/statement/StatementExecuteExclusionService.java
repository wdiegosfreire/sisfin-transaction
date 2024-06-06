package br.com.dfdevforge.sisfintransaction.statement.service.statement;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.DataForExclusionNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.statement.entities.StatementEntity;
import br.com.dfdevforge.sisfintransaction.statement.entities.StatementItemEntity;
import br.com.dfdevforge.sisfintransaction.statement.repositories.StatementRepository;
import br.com.dfdevforge.sisfintransaction.statement.service.statementitem.StatementItemExecuteExclusionService;

@Service
@RequestScope
@Transactional
public class StatementExecuteExclusionService extends StatementBaseService implements CommonService {
	private final StatementRepository statementRepository;
	private final StatementItemExecuteExclusionService statementItemExecuteExclusionService;

	private StatementEntity statementDelete;

	@Autowired
	public StatementExecuteExclusionService(StatementRepository statementRepository, StatementItemExecuteExclusionService statementItemExecuteExclusionService) {
		this.statementRepository = statementRepository;
		this.statementItemExecuteExclusionService = statementItemExecuteExclusionService;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findByIdentity();
		this.deleteAllItemsByStatement();
		this.deleteStatement();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("statementRegistered", this.statementParam);
		return super.returnBusinessData();
	}

	private void findByIdentity() throws DataForExclusionNotFoundException {
		this.statementDelete = this.statementRepository.findByIdentity(this.statementParam.getIdentity());

		if (this.statementDelete == null)
			throw new DataForExclusionNotFoundException();
	}

	private void deleteAllItemsByStatement() throws BaseException {
		for (StatementItemEntity statementItemDelete : this.statementDelete.getStatementItemList()) {
			this.statementItemExecuteExclusionService.setParams(statementItemDelete, this.token);
			this.statementItemExecuteExclusionService.execute();
		}
	}

	private void deleteStatement() {
		this.statementRepository.delete(this.statementDelete);
	}
}