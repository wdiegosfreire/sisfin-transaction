package br.com.dfdevforge.sisfintransaction.services.objectivemovement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dfdevforge.common.exceptions.BaseException;
import br.com.dfdevforge.common.services.CommonService;
import br.com.dfdevforge.sisfintransaction.repositories.ObjectiveMovementRepositoryCustomized;

@Service
public class ObjectiveMovementAccessModuleService extends ObjectiveMovementBaseService implements CommonService {
	@Autowired private ObjectiveMovementRepositoryCustomized objectiveMovementRepositoryCustomized;

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findObjectiveMovementsByUserAndPeriod();
	}

	private void findObjectiveMovementsByUserAndPeriod() {
		this.setArtifact("objectiveMovementList", this.objectiveMovementRepositoryCustomized.searchByPeriod(objectiveMovementParam));
	}
}