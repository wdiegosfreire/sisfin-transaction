package br.com.dfdevforge.sisfintransaction.services.objectivemovement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dfdevforge.common.exceptions.BaseException;
import br.com.dfdevforge.common.services.CommonService;
import br.com.dfdevforge.sisfintransaction.entities.ObjectiveMovementEntity;
import br.com.dfdevforge.sisfintransaction.exceptions.DataForEditionNotFoundException;
import br.com.dfdevforge.sisfintransaction.repositories.ObjectiveMovementRepository;

@Service
public class ObjectiveMovementAccessEditionService extends ObjectiveMovementBaseService implements CommonService {
	@Autowired private ObjectiveMovementRepository objectiveMovementRepository;

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findById();
	}

	private void findById() throws DataForEditionNotFoundException {
		ObjectiveMovementEntity objectiveMovement = this.objectiveMovementRepository.findByIdentity(this.objectiveMovementParam.getIdentity());

		if (objectiveMovement == null)
			throw new DataForEditionNotFoundException();

		this.setArtifact("objectiveMovement", objectiveMovement);
	}
}