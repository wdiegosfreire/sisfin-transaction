package br.com.dfdevforge.sisfintransaction.transaction.model.summary.services;

import br.com.dfdevforge.sisfintransaction.commons.services.BaseService;
import br.com.dfdevforge.sisfintransaction.transaction.model.summary.entities.SummaryEntity;

public abstract class SummaryBaseService extends BaseService {
	protected SummaryEntity summaryParam;

	public void setParams(SummaryEntity summaryEntity) {
		this.summaryParam = summaryEntity;
	}
}