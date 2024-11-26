package br.com.dfdevforge.sisfintransaction.transaction.model.objectivemovement.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.DataForEditionNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.transaction.model.objectivemovement.entities.ObjectiveMovementEntity;
import br.com.dfdevforge.sisfintransaction.transaction.model.objectivemovement.repositories.ObjectiveMovementRepository;

@Service
@RequestScope
@Transactional
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

	private void editObjectiveMovement() {
		this.objectiveMovementRepository.save(this.objectiveMovementParam);
	}

	private void findAllObjectiveMovements() {
		this.setArtifact("objectiveMovementList", this.objectiveMovementRepository.findByUserIdentity(objectiveMovementParam.getUserIdentity()));
	}
}