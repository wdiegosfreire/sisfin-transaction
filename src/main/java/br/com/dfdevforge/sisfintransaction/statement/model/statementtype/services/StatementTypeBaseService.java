package br.com.dfdevforge.sisfintransaction.statement.model.statementtype.services;

import br.com.dfdevforge.sisfintransaction.commons.services.BaseService;
import br.com.dfdevforge.sisfintransaction.statement.model.statementtype.entities.StatementTypeEntity;

public abstract class StatementTypeBaseService extends BaseService {
	protected StatementTypeEntity statementTypeParam;

	public void setParams(StatementTypeEntity statementTypeEntity) {
		this.statementTypeParam = statementTypeEntity;
	}
}