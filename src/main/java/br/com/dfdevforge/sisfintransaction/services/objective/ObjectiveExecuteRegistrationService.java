package br.com.dfdevforge.sisfintransaction.services.objective;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dfdevforge.common.exceptions.BaseException;
import br.com.dfdevforge.common.services.CommonService;
import br.com.dfdevforge.sisfintransaction.exceptions.RequiredFieldNotFoundException;
import br.com.dfdevforge.sisfintransaction.repositories.ObjectiveRepository;

@Service
public class ObjectiveExecuteRegistrationService extends ObjectiveBaseService implements CommonService {
	@Autowired private ObjectiveRepository objectiveRepository;

	@Override
	public void executeBusinessRule() throws BaseException {
		this.checkRequiredFields();
		this.saveAccount();
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

		if (errorList != null && !errorList.isEmpty())
			throw new RequiredFieldNotFoundException("Required Field Not Found", errorList);
	}

	private void saveAccount() throws BaseException {
		this.objectiveRepository.save(this.objectiveParam);
	}

	private void findAllAccounts() {
		this.setArtifact(OBJECTIVE_LIST, this.objectiveRepository.findByUserIdentity(this.objectiveParam.getUserIdentity()));
	}
}