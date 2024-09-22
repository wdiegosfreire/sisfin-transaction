package br.com.dfdevforge.sisfintransaction.transaction.services.objective;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.DataForExclusionNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.transaction.entities.ObjectiveEntity;
import br.com.dfdevforge.sisfintransaction.transaction.entities.ObjectiveItemEntity;
import br.com.dfdevforge.sisfintransaction.transaction.entities.ObjectiveMovementEntity;
import br.com.dfdevforge.sisfintransaction.transaction.repositories.ObjectiveItemRepository;
import br.com.dfdevforge.sisfintransaction.transaction.repositories.ObjectiveRepository;
import br.com.dfdevforge.sisfintransaction.transaction.repositories.objectivemovement.ObjectiveMovementRepository;

@Service
@RequestScope
@Transactional
public class ObjectiveExecuteExclusionService extends ObjectiveBaseService implements CommonService {
	private final ObjectiveRepository objectiveRepository;
	private final ObjectiveItemRepository objectiveItemRepository;
	private final ObjectiveMovementRepository objectiveMovementRepository;

	@Autowired
	public ObjectiveExecuteExclusionService(ObjectiveRepository objectiveRepository, ObjectiveItemRepository objectiveItemRepository, ObjectiveMovementRepository objectiveMovementRepository) {
		this.objectiveRepository = objectiveRepository;
		this.objectiveItemRepository = objectiveItemRepository;
		this.objectiveMovementRepository = objectiveMovementRepository;
	}

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

		this.objectiveDelete.setObjectiveMovementList(this.objectiveMovementRepository.findByObjective(this.objectiveDelete));
		this.objectiveDelete.setObjectiveItemList(this.objectiveItemRepository.findByObjective(this.objectiveDelete));
	}

	private void deleteAllMovementsByObjective() {
		for (ObjectiveMovementEntity objectiveMovementDelete : this.objectiveDelete.getObjectiveMovementList())
			this.objectiveMovementRepository.delete(objectiveMovementDelete);
	}

	private void deleteAllItemsByObjective() {
		for (ObjectiveItemEntity objectiveItemDelete : this.objectiveDelete.getObjectiveItemList())
			this.objectiveItemRepository.delete(objectiveItemDelete);
	}

	private void deleteObjective() {
		this.objectiveRepository.delete(this.objectiveDelete);
	}
}