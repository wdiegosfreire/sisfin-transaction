package br.com.dfdevforge.sisfintransaction.services.objective;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dfdevforge.common.exceptions.BaseException;
import br.com.dfdevforge.common.services.CommonService;
import br.com.dfdevforge.sisfintransaction.entities.ObjectiveEntity;
import br.com.dfdevforge.sisfintransaction.entities.ObjectiveItemEntity;
import br.com.dfdevforge.sisfintransaction.entities.ObjectiveMovementEntity;
import br.com.dfdevforge.sisfintransaction.exceptions.DataForEditionNotFoundException;
import br.com.dfdevforge.sisfintransaction.repositories.ObjectiveItemRepository;
import br.com.dfdevforge.sisfintransaction.repositories.ObjectiveMovementRepository;
import br.com.dfdevforge.sisfintransaction.repositories.ObjectiveRepository;

@Service
public class ObjectiveExecuteEditionService extends ObjectiveBaseService implements CommonService {
	@Autowired private ObjectiveRepository objectiveRepository;
	@Autowired private ObjectiveMovementRepository objectiveMovementRepository;
	@Autowired private ObjectiveItemRepository objectiveItemRepository;

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

	private void saveObjectiveEdition() throws BaseException {
		this.objectiveRepository.save(this.objectiveParam);
	}

	private void saveObjectiveMovementsEdition() throws BaseException {
		Date now = new Date();
		for (ObjectiveMovementEntity objectiveMovementEdited : this.objectiveParam.getObjectiveMovementList()) {
			objectiveMovementEdited.setRegistrationDate(now);
			objectiveMovementEdited.setObjective(this.objectiveParam);
			objectiveMovementEdited.setUserIdentity(this.objectiveParam.getUserIdentity());
			this.objectiveMovementRepository.save(objectiveMovementEdited);
		}
	}

	private void saveObjectiveItemsEdition() throws BaseException {
		for (ObjectiveItemEntity objectiveItemEdited : this.objectiveParam.getObjectiveItemList()) {
			objectiveItemEdited.setObjective(this.objectiveParam);
			objectiveItemEdited.setUserIdentity(this.objectiveParam.getUserIdentity());
			this.objectiveItemRepository.save(objectiveItemEdited);
		}
	}
}