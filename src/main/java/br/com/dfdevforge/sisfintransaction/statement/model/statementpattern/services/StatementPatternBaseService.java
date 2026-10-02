package br.com.dfdevforge.sisfintransaction.statement.model.statementpattern.services;

import br.com.dfdevforge.sisfintransaction.commons.services.BaseService;
import br.com.dfdevforge.sisfintransaction.statement.model.statementpattern.entities.StatementPatternEntity;

public abstract class StatementPatternBaseService extends BaseService {
	protected StatementPatternEntity statementPatternParam;

	public void setParams(StatementPatternEntity statementPatternEntity) {
		this.statementPatternParam = statementPatternEntity;
	}
}