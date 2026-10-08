package br.com.dfdevforge.sisfintransaction.transaction.model.objective.services;

import br.com.dfdevforge.sisfintransaction.commons.services.BaseService;
import br.com.dfdevforge.sisfintransaction.transaction.model.objective.entities.ObjectiveEntity;

public abstract class ObjectiveBaseService extends BaseService {
	protected static final String OBJECTIVE_LIST = "objectiveList";

	protected ObjectiveEntity objectiveParam;

	public void setParams(ObjectiveEntity objectiveEntity) {
		this.objectiveParam = objectiveEntity;
	}
}