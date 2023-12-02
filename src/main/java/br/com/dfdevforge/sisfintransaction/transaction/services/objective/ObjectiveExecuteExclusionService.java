package br.com.dfdevforge.sisfintransaction.transaction.services.objective;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.DataForExclusionNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.transaction.entities.ObjectiveEntity;
import br.com.dfdevforge.sisfintransaction.transaction.entities.ObjectiveItemEntity;
import br.com.dfdevforge.sisfintransaction.transaction.entities.ObjectiveMovementEntity;
import br.com.dfdevforge.sisfintransaction.transaction.repositories.ObjectiveItemRepository;
import br.com.dfdevforge.sisfintransaction.transaction.repositories.ObjectiveMovementRepository;
import br.com.dfdevforge.sisfintransaction.transaction.repositories.ObjectiveRepository;

@Service
@Transactional
public class ObjectiveExecuteExclusionService extends ObjectiveBaseService implements CommonService {
	@Autowired private ObjectiveRepository objectiveRepository;
	@Autowired private ObjectiveItemRepository objectiveItemRepository;
	@Autowired private ObjectiveMovementRepository objectiveMovementRepository;

	private ObjectiveEntity objectiveDelete;

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findByIdentity();
		this.deleteAllMovementsByObjective();
		this.deleteAllItemsByObjective();
		this.deleteObjective();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("accountRegistered", this.objectiveParam);
		return super.returnBusinessData();
	}

	private void findByIdentity() throws DataForExclusionNotFoundException {
		this.objectiveDelete = this.objectiveRepository.findByIdentity(this.objectiveParam.getIdentity());

		if (this.objectiveDelete == null)
			throw new DataForExclusionNotFoundException();
	}

	private void deleteAllMovementsByObjective() {
		for (ObjectiveMovementEntity objectiveMovementDelete : this.objectiveParam.getObjectiveMovementList())
			this.objectiveMovementRepository.delete(objectiveMovementDelete);
	}

	private void deleteAllItemsByObjective() {
		for (ObjectiveItemEntity objectiveItemDelete : this.objectiveParam.getObjectiveItemList())
			this.objectiveItemRepository.delete(objectiveItemDelete);
	}

	private void deleteObjective() throws BaseException {
		this.objectiveRepository.delete(this.objectiveDelete);
	}
}