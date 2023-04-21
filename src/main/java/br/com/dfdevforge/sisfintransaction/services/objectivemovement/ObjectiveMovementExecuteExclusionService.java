package br.com.dfdevforge.sisfintransaction.services.objectivemovement;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dfdevforge.common.exceptions.BaseException;
import br.com.dfdevforge.common.services.CommonService;
import br.com.dfdevforge.sisfintransaction.entities.ObjectiveMovementEntity;
import br.com.dfdevforge.sisfintransaction.exceptions.DataForExclusionNotFoundException;
import br.com.dfdevforge.sisfintransaction.repositories.ObjectiveMovementRepository;

@Service
public class ObjectiveMovementExecuteExclusionService extends ObjectiveMovementBaseService implements CommonService {
	@Autowired private ObjectiveMovementRepository objectiveMovementRepository;

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findById();
		this.deleteObjectiveMovement();
		this.findAllObjectiveMovements();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("objectiveMovementRegistered", this.objectiveMovementParam);
		return super.returnBusinessData();
	}

	private void findById() throws DataForExclusionNotFoundException {
		ObjectiveMovementEntity objectiveMovement = this.objectiveMovementRepository.findByIdentity(this.objectiveMovementParam.getIdentity());

		if (objectiveMovement == null)
			throw new DataForExclusionNotFoundException();
	}

	private void deleteObjectiveMovement() throws BaseException {
		this.objectiveMovementRepository.delete(this.objectiveMovementParam);
	}

	private void findAllObjectiveMovements() {
		this.setArtifact("objectiveMovementList", this.objectiveMovementRepository.findByUserIdentity(objectiveMovementParam.getUserIdentity()));
	}
}