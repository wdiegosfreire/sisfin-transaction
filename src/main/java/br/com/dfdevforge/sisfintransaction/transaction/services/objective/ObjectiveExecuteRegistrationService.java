package br.com.dfdevforge.sisfintransaction.transaction.services.objective;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.RequiredFieldNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.commons.utils.Utils;
import br.com.dfdevforge.sisfintransaction.transaction.entities.ObjectiveItemEntity;
import br.com.dfdevforge.sisfintransaction.transaction.entities.ObjectiveMovementEntity;
import br.com.dfdevforge.sisfintransaction.transaction.repositories.ObjectiveItemRepository;
import br.com.dfdevforge.sisfintransaction.transaction.repositories.ObjectiveRepository;
import br.com.dfdevforge.sisfintransaction.transaction.repositories.objectivemovement.ObjectiveMovementRepository;

@Service
@RequestScope
@Transactional
public class ObjectiveExecuteRegistrationService extends ObjectiveBaseService implements CommonService {
	private final ObjectiveRepository objectiveRepository;
	private final ObjectiveItemRepository objectiveItemRepository;
	private final ObjectiveMovementRepository objectiveMovementRepository;

	@Autowired
	public ObjectiveExecuteRegistrationService(ObjectiveRepository objectiveRepository, ObjectiveItemRepository objectiveItemRepository, ObjectiveMovementRepository objectiveMovementRepository) {
		this.objectiveRepository = objectiveRepository;
		this.objectiveItemRepository = objectiveItemRepository;
		this.objectiveMovementRepository = objectiveMovementRepository;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.checkRequiredFields();
		this.saveObjective();
		this.saveObjectiveMovements();
		this.saveObjectiveItems();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("accountRegistered", this.objectiveParam);
		return super.returnBusinessData();
	}

	private void checkRequiredFields() throws RequiredFieldNotFoundException {
		List<String> errorList = new ArrayList<>();

		if (this.objectiveParam.getDescription() == null || this.objectiveParam.getDescription().equals(""))
			errorList.add("Please, enter the name.");

		if (CollectionUtils.isEmpty(this.objectiveParam.getObjectiveMovementList()))
			errorList.add("Please, enter at least one movement.");

		if (CollectionUtils.isEmpty(this.objectiveParam.getObjectiveItemList()))
			errorList.add("Please, enter at least one item.");

		if (!errorList.isEmpty())
			throw new RequiredFieldNotFoundException("Required Field Not Found", errorList);
	}

	private void saveObjective() {
		if (this.objectiveParam.getLocation() != null && this.objectiveParam.getLocation().getIdentity() == null)
			this.objectiveParam.setLocation(null);

		this.objectiveRepository.save(this.objectiveParam);
	}

	private void saveObjectiveMovements() {
		Date now = new Date();
		for (ObjectiveMovementEntity objectiveMovementInsert : this.objectiveParam.getObjectiveMovementList()) {
			objectiveMovementInsert.setRegistrationDate(now);

			if (objectiveMovementInsert.getDueDate() != null)
				objectiveMovementInsert.setDueDate(Utils.date.setTime(objectiveMovementInsert.getDueDate(), 12, 0, 0));

			if (objectiveMovementInsert.getPaymentDate() != null)
				objectiveMovementInsert.setPaymentDate(Utils.date.setTime(objectiveMovementInsert.getPaymentDate(), 12, 0, 0));

			objectiveMovementInsert.setUserIdentity(this.objectiveParam.getUserIdentity());
			objectiveMovementInsert.setObjective(this.objectiveParam);
			this.objectiveMovementRepository.save(objectiveMovementInsert);
		}
	}

	private void saveObjectiveItems() {
		for (ObjectiveItemEntity objectiveItemInsert : this.objectiveParam.getObjectiveItemList()) {
			objectiveItemInsert.setObjective(this.objectiveParam);
			objectiveItemInsert.setUserIdentity(this.objectiveParam.getUserIdentity());
			this.objectiveItemRepository.save(objectiveItemInsert);
		}
	}
}