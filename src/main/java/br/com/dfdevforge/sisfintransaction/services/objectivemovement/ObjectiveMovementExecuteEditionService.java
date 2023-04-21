package br.com.dfdevforge.sisfintransaction.services.objectivemovement;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dfdevforge.common.exceptions.BaseException;
import br.com.dfdevforge.common.services.CommonService;
import br.com.dfdevforge.sisfintransaction.entities.ObjectiveMovementEntity;
import br.com.dfdevforge.sisfintransaction.exceptions.DataForEditionNotFoundException;
import br.com.dfdevforge.sisfintransaction.repositories.ObjectiveMovementRepository;

@Service
public class ObjectiveMovementExecuteEditionService extends ObjectiveMovementBaseService implements CommonService {
	@Autowired private ObjectiveMovementRepository objectiveMovementRepository;

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findById();
		this.editObjectiveMovement();
		this.findAllObjectiveMovements();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("objectiveMovementRegistered", this.objectiveMovementParam);
		return super.returnBusinessData();
	}

	private void findById() throws DataForEditionNotFoundException {
		ObjectiveMovementEntity objectiveMovement = this.objectiveMovementRepository.findByIdentity(this.objectiveMovementParam.getIdentity());

		if (objectiveMovement == null)
			throw new DataForEditionNotFoundException();
	}

	private void editObjectiveMovement() throws BaseException {
		this.objectiveMovementRepository.save(this.objectiveMovementParam);
	}

	private void findAllObjectiveMovements() {
		this.setArtifact("objectiveMovementList", this.objectiveMovementRepository.findByUserIdentity(objectiveMovementParam.getUserIdentity()));
	}
}