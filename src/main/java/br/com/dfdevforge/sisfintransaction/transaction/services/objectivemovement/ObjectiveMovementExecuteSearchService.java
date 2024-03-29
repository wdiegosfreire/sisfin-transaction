package br.com.dfdevforge.sisfintransaction.transaction.services.objectivemovement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.transaction.repositories.ObjectiveMovementRepositoryCustomized;

@Service
public class ObjectiveMovementExecuteSearchService extends ObjectiveMovementBaseService implements CommonService {
	@Autowired private ObjectiveMovementRepositoryCustomized objectiveMovementRepositoryCustomized;

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findAllObjectiveMovements();
	}

	private void findAllObjectiveMovements() {
		if (this.objectiveMovementParam.getFilter() == null || this.objectiveMovementParam.getFilter().contentEquals(""))
			this.setArtifact("objectiveMovementList", this.objectiveMovementRepositoryCustomized.searchByPeriod(this.objectiveMovementParam));
		else
			this.setArtifact("objectiveMovementList", this.objectiveMovementRepositoryCustomized.searchInAllPropertiesByPeriod(this.objectiveMovementParam));
	}
}