package br.com.dfdevforge.sisfintransaction.services.objective;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import br.com.dfdevforge.common.exceptions.BaseException;
import br.com.dfdevforge.common.services.CommonService;
import br.com.dfdevforge.sisfintransaction.entities.ObjectiveItemEntity;
import br.com.dfdevforge.sisfintransaction.entities.ObjectiveMovementEntity;
import br.com.dfdevforge.sisfintransaction.exceptions.RequiredFieldNotFoundException;
import br.com.dfdevforge.sisfintransaction.repositories.ObjectiveItemRepository;
import br.com.dfdevforge.sisfintransaction.repositories.ObjectiveMovementRepository;
import br.com.dfdevforge.sisfintransaction.repositories.ObjectiveRepository;

@Service
@Transactional
public class ObjectiveExecuteRegistrationService extends ObjectiveBaseService implements CommonService {
	@Autowired private ObjectiveRepository objectiveRepository;
	@Autowired private ObjectiveMovementRepository objectiveMovementRepository;
	@Autowired private ObjectiveItemRepository objectiveItemRepository;

	@Override
	public void executeBusinessRule() throws BaseException {
		this.checkRequiredFields();
		this.saveObjective();
		this.saveObjectiveMovements();
		this.saveObjectiveItems();

		this.findAllAccounts();
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

		if (this.objectiveParam.getLocation() == null || this.objectiveParam.getLocation().getIdentity() == null)
			errorList.add("Please, enter the location.");

		if (CollectionUtils.isEmpty(this.objectiveParam.getObjectiveMovementList()))
			errorList.add("Please, enter at least one movement.");

		if (CollectionUtils.isEmpty(this.objectiveParam.getObjectiveItemList()))
			errorList.add("Please, enter at least one item.");

		if (errorList != null && !errorList.isEmpty())
			throw new RequiredFieldNotFoundException("Required Field Not Found", errorList);
	}

	private void saveObjective() throws BaseException {
		this.objectiveRepository.save(this.objectiveParam);
	}

	private void saveObjectiveMovements() throws BaseException {
		Date now = new Date();
		for (ObjectiveMovementEntity objectiveMovementInsert : this.objectiveParam.getObjectiveMovementList()) {
			objectiveMovementInsert.setRegistrationDate(now);
			objectiveMovementInsert.setUserIdentity(this.objectiveParam.getUserIdentity());
			objectiveMovementInsert.setObjective(this.objectiveParam);
			this.objectiveMovementRepository.save(objectiveMovementInsert);
		}
	}

	private void saveObjectiveItems() throws BaseException {
		for (ObjectiveItemEntity objectiveItemInsert : this.objectiveParam.getObjectiveItemList()) {
			objectiveItemInsert.setObjective(this.objectiveParam);
			objectiveItemInsert.setUserIdentity(this.objectiveParam.getUserIdentity());
			this.objectiveItemRepository.save(objectiveItemInsert);
		}
	}

	private void findAllAccounts() {
		this.setArtifact(OBJECTIVE_LIST, this.objectiveRepository.findByUserIdentity(this.objectiveParam.getUserIdentity()));
	}
}