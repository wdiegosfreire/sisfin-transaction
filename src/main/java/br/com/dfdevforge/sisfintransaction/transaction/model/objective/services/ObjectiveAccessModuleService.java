package br.com.dfdevforge.sisfintransaction.transaction.model.objective.services;

import java.time.Instant;
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
import br.com.dfdevforge.sisfintransaction.transaction.model.account.entities.AccountEntity;
import br.com.dfdevforge.sisfintransaction.transaction.model.account.repositories.AccountRepositoryCustomized;
import br.com.dfdevforge.sisfintransaction.transaction.model.objective.entities.ObjectiveEntity;
import br.com.dfdevforge.sisfintransaction.transaction.model.objective.repositories.ObjectiveRepository;
import br.com.dfdevforge.sisfintransaction.transaction.model.objectiveitem.repositories.ObjectiveItemRepository;
import br.com.dfdevforge.sisfintransaction.transaction.model.objectivemovement.entities.ObjectiveMovementEntity;
import br.com.dfdevforge.sisfintransaction.transaction.model.objectivemovement.repositories.ObjectiveMovementRepository;
import br.com.dfdevforge.sisfintransaction.transaction.model.objectivemovement.repositories.ObjectiveMovementRepositorySelectByPeriodAndDynamicFilters;

@Service
@RequestScope
@Transactional
public class ObjectiveAccessModuleService extends ObjectiveBaseService implements CommonService {
	private final ObjectiveRepository objectiveRepository;
	private final ObjectiveItemRepository objectiveItemRepository;
	private final ObjectiveMovementRepository objectiveMovementRepository;

	private final AccountRepositoryCustomized accountRepositoryCustomized;
	private final ObjectiveMovementRepositorySelectByPeriodAndDynamicFilters objectiveMovementRepositorySelectByPeriodAndDynamicFilters;

	@Autowired
	public ObjectiveAccessModuleService(ObjectiveRepository objectiveRepository, ObjectiveItemRepository objectiveItemRepository, ObjectiveMovementRepository objectiveMovementRepository, AccountRepositoryCustomized accountRepositoryCustomized, ObjectiveMovementRepositorySelectByPeriodAndDynamicFilters objectiveMovementRepositorySelectByPeriodAndDynamicFilters) {
		this.objectiveRepository = objectiveRepository;
		this.objectiveItemRepository = objectiveItemRepository;
		this.objectiveMovementRepository = objectiveMovementRepository;
		this.accountRepositoryCustomized = accountRepositoryCustomized;
		this.objectiveMovementRepositorySelectByPeriodAndDynamicFilters = objectiveMovementRepositorySelectByPeriodAndDynamicFilters;
	}

	private List<AccountEntity> accountListBalanceCombo = new ArrayList<>();
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
		this.findBalanceAccounts();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("objectiveList", this.objectiveListResult);
		this.setArtifact("accountListBalanceCombo", this.accountListBalanceCombo);
		return super.returnBusinessData();
	}

	private void findMovementsByUserAndPeriod() {
		this.objectiveMovementListInPeriod = this.objectiveMovementRepositorySelectByPeriodAndDynamicFilters.execute(this.objectiveParam.getFilterMap(), this.objectiveParam.getUserIdentity());
	}

	private void findObjectivesRelatedToMovementsInPeriod() {
		List<Long> objectiveIdentityList = new ArrayList<>();
		this.objectiveMovementListInPeriod.forEach(objectiveMovement -> objectiveIdentityList.add(objectiveMovement.getObjective().getIdentity()));

		this.objectiveListResult = objectiveRepository.findByIdentityIn(objectiveIdentityList);
	}

	private void findItemsOfEachObjective() {
		this.objectiveListResult.forEach(objective -> objective.setObjectiveItemList(this.objectiveItemRepository.findByObjective(objective)));
	}

	private void findMovementsOfEachObjective() {
		this.objectiveListResult.forEach(objective -> objective.setObjectiveMovementList(this.objectiveMovementRepository.findByObjective(objective)));
	}

	private void identifyMovementOfPeriod() {
		Instant instant = Instant.parse((String) this.objectiveParam.getFilterMap().get("periodDate"));
		int selectedPeriod = Utils.date.getPeriodOf(Date.from(instant));

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

	private void findBalanceAccounts() {
		this.accountListBalanceCombo = this.accountRepositoryCustomized.searchAllTypeBalance(this.objectiveParam.getUserIdentity());
	}
}