package br.com.dfdevforge.sisfintransaction.transaction.services.objectivemovement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.transaction.repositories.ObjectiveMovementRepositoryCustomized;

@Service
@RequestScope
@Transactional
public class ObjectiveMovementExecuteSearchService extends ObjectiveMovementBaseService implements CommonService {
	private final ObjectiveMovementRepositoryCustomized objectiveMovementRepositoryCustomized;

	@Autowired
	public ObjectiveMovementExecuteSearchService(ObjectiveMovementRepositoryCustomized objectiveMovementRepositoryCustomized) {
		this.objectiveMovementRepositoryCustomized = objectiveMovementRepositoryCustomized;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findAllObjectiveMovements();
	}

	private void findAllObjectiveMovements() {
		if (this.objectiveMovementParam.getFilter() == null || this.objectiveMovementParam.getFilter().contentEquals(""))
			this.setArtifact("objectiveMovementList", this.objectiveMovementRepositoryCustomized.searchByPeriod(this.objectiveMovementParam.getPaymentDate(), this.objectiveMovementParam.getUserIdentity()));
		else
			this.setArtifact("objectiveMovementList", this.objectiveMovementRepositoryCustomized.searchInAllPropertiesByPeriod(this.objectiveMovementParam));
	}
}