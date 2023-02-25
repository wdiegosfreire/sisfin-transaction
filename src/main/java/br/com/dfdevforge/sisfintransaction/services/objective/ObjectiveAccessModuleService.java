package br.com.dfdevforge.sisfintransaction.services.objective;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dfdevforge.common.exceptions.BaseException;
import br.com.dfdevforge.common.services.CommonService;
import br.com.dfdevforge.sisfintransaction.entities.ObjectiveEntity;
import br.com.dfdevforge.sisfintransaction.repositories.ObjectiveRepository;

@Service
public class ObjectiveAccessModuleService extends ObjectiveBaseService implements CommonService {
	private List<ObjectiveEntity> objectiveList;

	@Autowired private ObjectiveRepository objectiveRepository;

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findAllObjectivesByMonthAndYear();
	}

	private void findAllObjectivesByMonthAndYear() {
		this.objectiveList = this.objectiveRepository.findByUserIdentity(this.objectiveParam.getUserIdentity());
		this.setArtifact(OBJECTIVE_LIST, this.objectiveRepository.findByUserIdentity(this.objectiveParam.getUserIdentity()));
	}

	
}