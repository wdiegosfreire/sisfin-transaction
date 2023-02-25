package br.com.dfdevforge.sisfintransaction.services.objective;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dfdevforge.common.exceptions.BaseException;
import br.com.dfdevforge.common.services.CommonService;
import br.com.dfdevforge.sisfintransaction.entities.ObjectiveEntity;
import br.com.dfdevforge.sisfintransaction.exceptions.DataForExclusionNotFoundException;
import br.com.dfdevforge.sisfintransaction.repositories.ObjectiveRepository;

@Service
public class ObjectiveExecuteExclusionService extends ObjectiveBaseService implements CommonService {
	@Autowired private ObjectiveRepository objectiveRepository;

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findById();
		this.deleteObjective();
		this.findAllAccounts();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("accountRegistered", this.objectiveParam);
		return super.returnBusinessData();
	}

	private void findById() throws DataForExclusionNotFoundException {
		ObjectiveEntity objective = this.objectiveRepository.findByIdentity(this.objectiveParam.getIdentity());

		if (objective == null)
			throw new DataForExclusionNotFoundException();
	}

	private void deleteObjective() throws BaseException {
		this.objectiveRepository.delete(this.objectiveParam);
	}

	private void findAllAccounts() {
		this.setArtifact(OBJECTIVE_LIST, this.objectiveRepository.findByUserIdentity(this.objectiveParam.getUserIdentity()));
	}
}