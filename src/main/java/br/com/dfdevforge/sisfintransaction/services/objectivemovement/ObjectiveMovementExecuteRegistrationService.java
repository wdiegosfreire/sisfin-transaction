package br.com.dfdevforge.sisfintransaction.services.objectivemovement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dfdevforge.common.exceptions.BaseException;
import br.com.dfdevforge.common.services.CommonService;
import br.com.dfdevforge.sisfintransaction.exceptions.RequiredFieldNotFoundException;
import br.com.dfdevforge.sisfintransaction.repositories.ObjectiveMovementRepository;

@Service
public class ObjectiveMovementExecuteRegistrationService extends ObjectiveMovementBaseService implements CommonService {
	@Autowired private ObjectiveMovementRepository objectiveMovementRepository;

	@Override
	public void executeBusinessRule() throws BaseException {
		this.checkRequiredFields();
		this.saveObjectiveMovement();
		this.findAllObjectiveMovements();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("objectiveMovementRegistered", this.objectiveMovementParam);
		return super.returnBusinessData();
	}

	private void checkRequiredFields() throws RequiredFieldNotFoundException {
		List<String> errorList = new ArrayList<>();

		if (this.objectiveMovementParam.getRegistrationDate() == null)
			errorList.add("Please, enter CNPJ.");
		if (this.objectiveMovementParam.getUserIdentity() == null)
			errorList.add("Please, the movement need to be associated with a user.");

		if (errorList != null && !errorList.isEmpty())
			throw new RequiredFieldNotFoundException("Required Field Not Found", errorList);
	}

	private void saveObjectiveMovement() throws BaseException {
		this.objectiveMovementRepository.save(this.objectiveMovementParam);
	}

	private void findAllObjectiveMovements() {
		this.setArtifact("objectiveMovementList", this.objectiveMovementRepository.findByUserIdentity(objectiveMovementParam.getUserIdentity()));
	}
}