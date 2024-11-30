package br.com.dfdevforge.sisfintransaction.statement.model.statementitem.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.DataForExclusionNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.statement.model.statementitem.entities.StatementItemEntity;
import br.com.dfdevforge.sisfintransaction.statement.model.statementitem.repositories.StatementItemRepository;

@Service
@RequestScope
@Transactional
public class StatementItemExecuteExclusionService extends StatementItemBaseService implements CommonService {
	private final StatementItemRepository statementItemRepository;

	private StatementItemEntity statementItemDelete;

	@Autowired
	public StatementItemExecuteExclusionService(StatementItemRepository statementItemRepository) {
		this.statementItemRepository = statementItemRepository;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findById();
		this.deleteStatementItem();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("statementItemDeleted", this.statementItemParam);
		return super.returnBusinessData();
	}

	private void findById() throws DataForExclusionNotFoundException {
		this.statementItemDelete = this.statementItemRepository.findByIdentity(this.statementItemParam.getIdentity());

		if (this.statementItemDelete == null)
			throw new DataForExclusionNotFoundException();
	}

	private void deleteStatementItem() {
		this.statementItemRepository.delete(this.statementItemDelete);
	}
}