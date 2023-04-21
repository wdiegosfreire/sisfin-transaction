package br.com.dfdevforge.sisfintransaction.services.objective;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dfdevforge.common.exceptions.BaseException;
import br.com.dfdevforge.common.services.CommonService;
import br.com.dfdevforge.sisfintransaction.entities.ObjectiveEntity;
import br.com.dfdevforge.sisfintransaction.exceptions.DataForEditionNotFoundException;
import br.com.dfdevforge.sisfintransaction.repositories.ObjectiveRepository;

@Service
public class ObjectiveAccessEditionService extends ObjectiveBaseService implements CommonService {
	@Autowired private ObjectiveRepository objectiveRepository;

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findById();
	}

	private void findById() throws DataForEditionNotFoundException {
		ObjectiveEntity objective = this.objectiveRepository.findByIdentity(this.objectiveParam.getIdentity());

		if (objective == null)
			throw new DataForEditionNotFoundException();

		this.setArtifact("objective", objective);
	}
}