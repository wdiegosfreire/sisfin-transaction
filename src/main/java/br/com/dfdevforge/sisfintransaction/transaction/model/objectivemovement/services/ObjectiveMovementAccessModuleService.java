package br.com.dfdevforge.sisfintransaction.transaction.model.objectivemovement.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.DataForEditionNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.transaction.model.objectiveitem.entities.ObjectiveItemEntity;
import br.com.dfdevforge.sisfintransaction.transaction.model.objectiveitem.repositories.ObjectiveItemRepository;
import br.com.dfdevforge.sisfintransaction.transaction.model.objectivemovement.entities.ObjectiveMovementEntity;
import br.com.dfdevforge.sisfintransaction.transaction.model.objectivemovement.repositories.ObjectiveMovementRepository;
import br.com.dfdevforge.sisfintransaction.transaction.model.objectivemovement.repositories.ObjectiveMovementRepositoryCustomized;

@Service
@RequestScope
@Transactional
public class ObjectiveMovementAccessModuleService extends ObjectiveMovementBaseService implements CommonService {
	private final ObjectiveItemRepository objectiveItemRepository;
	private final ObjectiveMovementRepository objectiveMovementRepository;
	private final ObjectiveMovementRepositoryCustomized objectiveMovementRepositoryCustomized;

	@Autowired
	public ObjectiveMovementAccessModuleService(ObjectiveItemRepository objectiveItemRepository, ObjectiveMovementRepository objectiveMovementRepository, ObjectiveMovementRepositoryCustomized objectiveMovementRepositoryCustomized) {
		this.objectiveItemRepository = objectiveItemRepository;
		this.objectiveMovementRepository = objectiveMovementRepository;
		this.objectiveMovementRepositoryCustomized = objectiveMovementRepositoryCustomized;
	}

	private List<ObjectiveMovementEntity> objectiveMovementListResult;

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findObjectiveMovementsByUserAndPeriod();
		this.findItemsListByObjective();
		this.findMovementsListByObjective();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("objectiveMovementList", this.objectiveMovementRepositoryCustomized.searchByPeriod(this.objectiveMovementParam.getPaymentDate(), this.objectiveMovementParam.getUserIdentity()));
		return super.returnBusinessData();
	}

	private void findObjectiveMovementsByUserAndPeriod() {
		this.objectiveMovementListResult = this.objectiveMovementRepositoryCustomized.searchByPeriod(this.objectiveMovementParam.getPaymentDate(), this.objectiveMovementParam.getUserIdentity());
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