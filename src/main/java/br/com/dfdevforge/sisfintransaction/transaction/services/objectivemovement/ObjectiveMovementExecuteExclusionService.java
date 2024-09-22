package br.com.dfdevforge.sisfintransaction.transaction.services.objectivemovement;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.DataForExclusionNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.transaction.entities.ObjectiveMovementEntity;
import br.com.dfdevforge.sisfintransaction.transaction.repositories.ObjectiveItemRepository;
import br.com.dfdevforge.sisfintransaction.transaction.repositories.objectivemovement.ObjectiveMovementRepository;
import br.com.dfdevforge.sisfintransaction.transaction.repositories.objectivemovement.ObjectiveMovementRepositoryCustomized;

@Service
@RequestScope
@Transactional
public class ObjectiveMovementExecuteExclusionService extends ObjectiveMovementBaseService implements CommonService {
	private final ObjectiveItemRepository objectiveItemRepository;
	private final ObjectiveMovementRepository objectiveMovementRepository;
	private final ObjectiveMovementRepositoryCustomized objectiveMovementRepositoryCustomized;

	@Autowired
	public ObjectiveMovementExecuteExclusionService(ObjectiveItemRepository objectiveItemRepository, ObjectiveMovementRepository objectiveMovementRepository, ObjectiveMovementRepositoryCustomized objectiveMovementRepositoryCustomized) {
		this.objectiveItemRepository = objectiveItemRepository;
		this.objectiveMovementRepository = objectiveMovementRepository;
		this.objectiveMovementRepositoryCustomized = objectiveMovementRepositoryCustomized;
	}

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

	private void deleteAllItemsByObjective() {
		this.objectiveItemRepository.deleteByObjective(this.objectiveMovementExclusion.getObjective());
	}

	private void deleteAllMovementsByObjective() {
		this.objectiveMovementRepository.deleteByObjective(this.objectiveMovementExclusion.getObjective());
	}

	private void findAllObjectiveMovements() {
		this.setArtifact("objectiveMovementList", this.objectiveMovementRepositoryCustomized.searchByPeriod(this.objectiveMovementParam.getPaymentDate(), this.objectiveMovementParam.getUserIdentity()));
	}
}