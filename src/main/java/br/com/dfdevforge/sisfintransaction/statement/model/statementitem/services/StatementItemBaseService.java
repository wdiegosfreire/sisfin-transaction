package br.com.dfdevforge.sisfintransaction.statement.model.statementitem.services;

import br.com.dfdevforge.sisfintransaction.commons.services.BaseService;
import br.com.dfdevforge.sisfintransaction.statement.model.statementitem.entities.StatementItemEntity;

public abstract class StatementItemBaseService extends BaseService {
	protected StatementItemEntity statementItemParam;

	public void setParams(StatementItemEntity statementItemEntity) {
		this.statementItemParam = statementItemEntity;
	}
}