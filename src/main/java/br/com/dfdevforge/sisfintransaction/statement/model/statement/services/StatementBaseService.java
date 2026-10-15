package br.com.dfdevforge.sisfintransaction.statement.model.statement.services;

import br.com.dfdevforge.sisfintransaction.commons.services.BaseService;
import br.com.dfdevforge.sisfintransaction.statement.model.statement.entities.StatementEntity;

public abstract class StatementBaseService extends BaseService {
	protected StatementEntity statementParam;

	public void setParams(StatementEntity statementEntity) {
		this.statementParam = statementEntity;
	}
}