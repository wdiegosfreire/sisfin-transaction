package br.com.dfdevforge.sisfintransaction.transaction.model.objectivemovement.services;

import br.com.dfdevforge.sisfintransaction.commons.services.BaseService;
import br.com.dfdevforge.sisfintransaction.transaction.model.objectivemovement.entities.ObjectiveMovementEntity;

public abstract class ObjectiveMovementBaseService extends BaseService {
	protected ObjectiveMovementEntity objectiveMovementParam;

	public void setParams(ObjectiveMovementEntity objectiveMovementEntity) {
		this.objectiveMovementParam = objectiveMovementEntity;
	}
}