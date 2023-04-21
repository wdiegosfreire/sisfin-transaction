package br.com.dfdevforge.sisfintransaction.services.objective;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dfdevforge.common.exceptions.BaseException;
import br.com.dfdevforge.common.services.CommonService;
import br.com.dfdevforge.sisfintransaction.entities.ObjectiveEntity;
import br.com.dfdevforge.sisfintransaction.exceptions.DataForEditionNotFoundException;
import br.com.dfdevforge.sisfintransaction.repositories.ObjectiveRepository;

@Service
public class ObjectiveExecuteEditionService extends ObjectiveBaseService implements CommonService {
	@Autowired private ObjectiveRepository objectiveRepository;

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findById();
		this.editObjective();
		this.findAllObjectives();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("objectiveRegistered", this.objectiveParam);
		return super.returnBusinessData();
	}

	private void findById() throws DataForEditionNotFoundException {
		ObjectiveEntity objective = this.objectiveRepository.findByIdentity(this.objectiveParam.getIdentity());

		if (objective == null)
			throw new DataForEditionNotFoundException();
	}

	private void editObjective() throws BaseException {
		this.objectiveRepository.save(this.objectiveParam);
	}

	private void findAllObjectives() {
		this.setArtifact(OBJECTIVE_LIST, this.objectiveRepository.findByUserIdentity(this.objectiveParam.getUserIdentity()));
	}
}