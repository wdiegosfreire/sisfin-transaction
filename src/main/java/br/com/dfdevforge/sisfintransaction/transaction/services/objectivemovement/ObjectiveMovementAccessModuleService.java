package br.com.dfdevforge.sisfintransaction.transaction.services.objectivemovement;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.DataForEditionNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.transaction.entities.ObjectiveItemEntity;
import br.com.dfdevforge.sisfintransaction.transaction.entities.ObjectiveMovementEntity;
import br.com.dfdevforge.sisfintransaction.transaction.repositories.ObjectiveItemRepository;
import br.com.dfdevforge.sisfintransaction.transaction.repositories.ObjectiveMovementRepository;
import br.com.dfdevforge.sisfintransaction.transaction.repositories.ObjectiveMovementRepositoryCustomized;

@Service
public class ObjectiveMovementAccessModuleService extends ObjectiveMovementBaseService implements CommonService {
	@Autowired private ObjectiveItemRepository objectiveItemRepository;
	@Autowired private ObjectiveMovementRepository objectiveMovementRepository;
	@Autowired private ObjectiveMovementRepositoryCustomized objectiveMovementRepositoryCustomized;

	@PersistenceContext
    private EntityManager entityManager;

	private List<ObjectiveMovementEntity> objectiveMovementListResult;

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findObjectiveMovementsByUserAndPeriod();
		this.findItemsListByObjective();
		this.findMovementsListByObjective();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("objectiveMovementList", this.objectiveMovementRepositoryCustomized.searchByPeriod(objectiveMovementParam));
		return super.returnBusinessData();
	}

	private void findObjectiveMovementsByUserAndPeriod() {
		this.objectiveMovementListResult = this.objectiveMovementRepositoryCustomized.searchByPeriod(objectiveMovementParam);
	}

	private void findItemsListByObjective() throws DataForEditionNotFoundException {
		for (ObjectiveMovementEntity objectiveMovementLoop : this.objectiveMovementListResult) {
			List<ObjectiveItemEntity> objectiveItemList = this.objectiveItemRepository.findByObjective(objectiveMovementLoop.getObjective());

			if (CollectionUtils.isEmpty(objectiveItemList))
				throw new DataForEditionNotFoundException();

			objectiveMovementLoop.getObjective().setObjectiveItemList(objectiveItemList);
		}
	}

	private void findMovementsListByObjective() throws DataForEditionNotFoundException {
		for (ObjectiveMovementEntity objectiveMovementLoop : this.objectiveMovementListResult) {
			List<ObjectiveMovementEntity> objectiveMovementList = this.objectiveMovementRepository.findByObjective(objectiveMovementLoop.getObjective());

			if (CollectionUtils.isEmpty(objectiveMovementList))
				throw new DataForEditionNotFoundException();

			objectiveMovementLoop.getObjective().setObjectiveMovementList(objectiveMovementList);
		}
	}
}