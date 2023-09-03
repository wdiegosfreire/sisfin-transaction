package br.com.dfdevforge.sisfintransaction.services.objectivemovement;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dfdevforge.common.exceptions.BaseException;
import br.com.dfdevforge.common.services.CommonService;
import br.com.dfdevforge.sisfintransaction.entities.ObjectiveMovementEntity;
import br.com.dfdevforge.sisfintransaction.exceptions.DataForExclusionNotFoundException;
import br.com.dfdevforge.sisfintransaction.repositories.ObjectiveItemRepository;
import br.com.dfdevforge.sisfintransaction.repositories.ObjectiveMovementRepository;
import br.com.dfdevforge.sisfintransaction.repositories.ObjectiveMovementRepositoryCustomized;

@Service
public class ObjectiveMovementExecuteExclusionService extends ObjectiveMovementBaseService implements CommonService {
	@Autowired private ObjectiveItemRepository objectiveItemRepository;
	@Autowired private ObjectiveMovementRepository objectiveMovementRepository;
	@Autowired private ObjectiveMovementRepositoryCustomized objectiveMovementRepositoryCustomized;

	private ObjectiveMovementEntity objectiveMovementExclusion;

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findById();
		this.deleteAllItemsByObjective();
		this.deleteAllMovementsByObjective();
		this.findAllObjectiveMovements();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("objectiveMovementRegistered", this.objectiveMovementParam);
		return super.returnBusinessData();
	}

	private void findById() throws DataForExclusionNotFoundException {
		this.objectiveMovementExclusion = this.objectiveMovementRepository.findByIdentity(this.objectiveMovementParam.getIdentity());

		if (this.objectiveMovementExclusion == null)
			throw new DataForExclusionNotFoundException();
	}

	private void deleteAllItemsByObjective() throws BaseException {
		this.objectiveItemRepository.deleteByObjective(this.objectiveMovementExclusion.getObjective());
	}

	private void deleteAllMovementsByObjective() throws BaseException {
		this.objectiveMovementRepository.deleteByObjective(this.objectiveMovementExclusion.getObjective());
	}

	private void findAllObjectiveMovements() {
		this.setArtifact("objectiveMovementList", this.objectiveMovementRepositoryCustomized.searchByPeriod(this.objectiveMovementParam));
	}
}