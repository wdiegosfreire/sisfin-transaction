package br.com.dfdevforge.sisfintransaction.transaction.model.objective.services;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.DataForEditionNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.transaction.model.objective.entities.ObjectiveEntity;
import br.com.dfdevforge.sisfintransaction.transaction.model.objective.repositories.ObjectiveRepository;
import br.com.dfdevforge.sisfintransaction.transaction.model.objectiveitem.entities.ObjectiveItemEntity;
import br.com.dfdevforge.sisfintransaction.transaction.model.objectiveitem.repositories.ObjectiveItemRepository;
import br.com.dfdevforge.sisfintransaction.transaction.model.objectivemovement.entities.ObjectiveMovementEntity;
import br.com.dfdevforge.sisfintransaction.transaction.model.objectivemovement.repositories.ObjectiveMovementRepository;

@Service
@RequestScope
@Transactional
public class ObjectiveExecuteEditionService extends ObjectiveBaseService implements CommonService {
	private final ObjectiveRepository objectiveRepository;
	private final ObjectiveMovementRepository objectiveMovementRepository;
	private final ObjectiveItemRepository objectiveItemRepository;

	@Autowired
	public ObjectiveExecuteEditionService(ObjectiveRepository objectiveRepository, ObjectiveMovementRepository objectiveMovementRepository, ObjectiveItemRepository objectiveItemRepository) {
		this.objectiveRepository = objectiveRepository;
		this.objectiveMovementRepository = objectiveMovementRepository;
		this.objectiveItemRepository = objectiveItemRepository;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findByIdentity();
		this.saveObjectiveEdition();
		this.saveObjectiveMovementsEdition();
		this.saveObjectiveItemsEdition();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("objectiveRegistered", this.objectiveParam);
		return super.returnBusinessData();
	}

	private void findByIdentity() throws DataForEditionNotFoundException {
		ObjectiveEntity objective = this.objectiveRepository.findByIdentity(this.objectiveParam.getIdentity());

		if (objective == null)
			throw new DataForEditionNotFoundException();
	}

	private void saveObjectiveEdition() {
		this.objectiveRepository.save(this.objectiveParam);
	}

	private void saveObjectiveMovementsEdition() {
		Date now = new Date();
		for (ObjectiveMovementEntity objectiveMovementEdited : this.objectiveParam.getObjectiveMovementList()) {
			objectiveMovementEdited.setRegistrationDate(now);
			objectiveMovementEdited.setObjective(this.objectiveParam);
			objectiveMovementEdited.setUserIdentity(this.objectiveParam.getUserIdentity());
			this.objectiveMovementRepository.save(objectiveMovementEdited);
		}
	}

	private void saveObjectiveItemsEdition() {
		for (ObjectiveItemEntity objectiveItemEdited : this.objectiveParam.getObjectiveItemList()) {
			objectiveItemEdited.setObjective(this.objectiveParam);
			objectiveItemEdited.setUserIdentity(this.objectiveParam.getUserIdentity());
			this.objectiveItemRepository.save(objectiveItemEdited);
		}
	}
}