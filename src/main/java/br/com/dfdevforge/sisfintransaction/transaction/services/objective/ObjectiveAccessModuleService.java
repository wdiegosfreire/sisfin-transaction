package br.com.dfdevforge.sisfintransaction.transaction.services.objective;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.commons.utils.Utils;
import br.com.dfdevforge.sisfintransaction.transaction.entities.ObjectiveEntity;
import br.com.dfdevforge.sisfintransaction.transaction.entities.ObjectiveMovementEntity;
import br.com.dfdevforge.sisfintransaction.transaction.repositories.ObjectiveItemRepository;
import br.com.dfdevforge.sisfintransaction.transaction.repositories.ObjectiveRepository;
import br.com.dfdevforge.sisfintransaction.transaction.repositories.objectivemovement.ObjectiveMovementRepository;
import br.com.dfdevforge.sisfintransaction.transaction.repositories.objectivemovement.ObjectiveMovementRepositoryCustomized;

@Service
@RequestScope
@Transactional
public class ObjectiveAccessModuleService extends ObjectiveBaseService implements CommonService {
	private final ObjectiveRepository objectiveRepository;
	private final ObjectiveItemRepository objectiveItemRepository;
	private final ObjectiveMovementRepository objectiveMovementRepository;
	private final ObjectiveMovementRepositoryCustomized objectiveMovementRepositoryCustomized;

	@Autowired
	public ObjectiveAccessModuleService(ObjectiveRepository objectiveRepository, ObjectiveItemRepository objectiveItemRepository, ObjectiveMovementRepository objectiveMovementRepository, ObjectiveMovementRepositoryCustomized objectiveMovementRepositoryCustomized) {
		this.objectiveRepository = objectiveRepository;
		this.objectiveItemRepository = objectiveItemRepository;
		this.objectiveMovementRepository = objectiveMovementRepository;
		this.objectiveMovementRepositoryCustomized = objectiveMovementRepositoryCustomized;
	}

	private List<ObjectiveEntity> objectiveListResult = new ArrayList<>();
	private List<ObjectiveMovementEntity> objectiveMovementListInPeriod = new ArrayList<>();

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findMovementsByUserAndPeriod();
		this.findObjectivesRelatedToMovementsInPeriod();
		this.findItemsOfEachObjective();
		this.findMovementsOfEachObjective();
		this.identifyMovementOfPeriod();
		this.sortObjectivesBySortDate();
		this.identifyNewHeaderGroup();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("objectiveList", this.objectiveListResult);
		return super.returnBusinessData();
	}

	private void findMovementsByUserAndPeriod() {
		this.objectiveMovementListInPeriod = this.objectiveMovementRepositoryCustomized.searchByPeriod(this.objectiveParam.getObjectiveMovementList().get(0).getPaymentDate(), this.objectiveParam.getUserIdentity());
	}

	private void findObjectivesRelatedToMovementsInPeriod() {
		List<Long> objectiveIdentityList = new ArrayList<>();
		this.objectiveMovementListInPeriod.forEach(objectiveMovement -> objectiveIdentityList.add(objectiveMovement.getObjective().getIdentity()));

		this.objectiveListResult = objectiveRepository.findByIdentityIn(objectiveIdentityList);
	}

	private void findItemsOfEachObjective() {
		this.objectiveListResult.forEach(objective -> objective.setObjectiveItemList(objectiveItemRepository.findByObjective(objective)));
	}

	private void findMovementsOfEachObjective() {
		this.objectiveListResult.forEach(objective -> objective.setObjectiveMovementList(objectiveMovementRepository.findByObjective(objective)));
	}

	private void identifyMovementOfPeriod() {
		int selectedPeriod = Utils.date.getPeriodOf(this.objectiveParam.getObjectiveMovementList().get(0).getPaymentDate());

		for (ObjectiveEntity objective : this.objectiveListResult) {
			for (ObjectiveMovementEntity objectiveMovement : objective.getObjectiveMovementList()) {
				Date movementDate = (objectiveMovement.getPaymentDate() != null ? objectiveMovement.getPaymentDate() : objectiveMovement.getDueDate());
				int movementPeriod = Utils.date.getPeriodOf(movementDate);

				if (movementPeriod == selectedPeriod) {
					objectiveMovement.setInPeriod(true);
					objective.setSortDate(movementDate);
				}
			}
		}
	}

	private void sortObjectivesBySortDate() {
		this.objectiveListResult = this.objectiveListResult.stream().sorted(Comparator.comparing(ObjectiveEntity::getSortDate)).collect(Collectors.toList());
	}

	private void identifyNewHeaderGroup() {
		String headerDate = "";
		
		for (ObjectiveEntity objective : this.objectiveListResult) {
			for (ObjectiveMovementEntity objectiveMovement : objective.getObjectiveMovementList()) {
				Date checkDate = objectiveMovement.getPaymentDate();
				if (checkDate == null)
					checkDate = objectiveMovement.getDueDate();

				if (!headerDate.equals(checkDate.toString())) {
					headerDate = checkDate.toString();
					objectiveMovement.getProps().setIsNewHeader(Boolean.TRUE);
				}
			}
		}
	}
}