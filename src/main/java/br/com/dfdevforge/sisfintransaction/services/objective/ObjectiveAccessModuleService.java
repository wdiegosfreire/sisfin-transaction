package br.com.dfdevforge.sisfintransaction.services.objective;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dfdevforge.common.exceptions.BaseException;
import br.com.dfdevforge.common.services.CommonService;
import br.com.dfdevforge.common.util.Utils;
import br.com.dfdevforge.sisfintransaction.entities.ObjectiveEntity;
import br.com.dfdevforge.sisfintransaction.entities.ObjectiveMovementEntity;
import br.com.dfdevforge.sisfintransaction.repositories.ObjectiveItemRepository;
import br.com.dfdevforge.sisfintransaction.repositories.ObjectiveMovementRepository;
import br.com.dfdevforge.sisfintransaction.repositories.ObjectiveMovementRepositoryCustomized;
import br.com.dfdevforge.sisfintransaction.repositories.ObjectiveRepository;

@Service
public class ObjectiveAccessModuleService extends ObjectiveBaseService implements CommonService {
	@Autowired private ObjectiveRepository objectiveRepository;
	@Autowired private ObjectiveItemRepository objectiveItemRepository;
	@Autowired private ObjectiveMovementRepository objectiveMovementRepository;

	@Autowired private ObjectiveMovementRepositoryCustomized objectiveMovementRepositoryCustomized;

	private List<ObjectiveMovementEntity> objectiveMovementListInPeriod;
	private List<ObjectiveEntity> objectiveListResult;

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findMovementsByUserAndPeriod();
		this.findObjectivesRelatedToMovementsInPeriod();
		this.findItemsOfEachObjective();
		this.findMovementsOfEachObjective();
		this.identifyMovementOfPeriod();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("objectiveList", this.objectiveListResult);
		return super.returnBusinessData();
	}

	private void findMovementsByUserAndPeriod() {
		this.objectiveMovementListInPeriod = this.objectiveMovementRepositoryCustomized.searchByPeriod(objectiveParam.getObjectiveMovementList().get(0));
	}

	private void findObjectivesRelatedToMovementsInPeriod() {
		List<Long> objectiveIdentityList = new ArrayList<Long>();
		this.objectiveMovementListInPeriod.forEach(objectiveMovement -> {
			objectiveIdentityList.add(objectiveMovement.getObjective().getIdentity());
		});

		this.objectiveListResult = objectiveRepository.findByIdentityIn(objectiveIdentityList);
	}

	private void findItemsOfEachObjective() {
		this.objectiveListResult.forEach(objective -> {
			objective.setObjectiveItemList(objectiveItemRepository.findByObjective(objective));			
		});
	}

	private void findMovementsOfEachObjective() {
		this.objectiveListResult.forEach(objective -> {
			objective.setObjectiveMovementList(objectiveMovementRepository.findByObjective(objective));
		});
	}

	private void identifyMovementOfPeriod() {
		int selectedPeriod = Utils.date.getPeriodOf(objectiveParam.getObjectiveMovementList().get(0).getPaymentDate());

		for (ObjectiveEntity objective : objectiveListResult) {
			for (ObjectiveMovementEntity objectiveMovement : objective.getObjectiveMovementList()) {
				Date movementDate = (objectiveMovement.getPaymentDate() != null ? objectiveMovement.getPaymentDate() : objectiveMovement.getDueDate());
				int movementPeriod = Utils.date.getPeriodOf(movementDate);

				if (movementPeriod == selectedPeriod)
					objectiveMovement.setInPeriod(true);
			}
		}
	}
}