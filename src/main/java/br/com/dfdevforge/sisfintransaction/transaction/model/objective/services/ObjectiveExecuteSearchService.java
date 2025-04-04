package br.com.dfdevforge.sisfintransaction.transaction.model.objective.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.transaction.model.objective.repositories.ObjectiveRepository;
import br.com.dfdevforge.sisfintransaction.transaction.model.objective.repositories.ObjectiveRepositoryCustomized;

@Service
@RequestScope
@Transactional
public class ObjectiveExecuteSearchService extends ObjectiveBaseService implements CommonService {
	@Autowired private ObjectiveRepository accountRepository;
	@Autowired private ObjectiveRepositoryCustomized accountRepositoryCustomized;

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findAllAccounts();
	}

	private void findAllAccounts() {
		if (this.objectiveParam.getFilter() == null || this.objectiveParam.getFilter().contentEquals(""))
			this.setArtifact(OBJECTIVE_LIST, this.accountRepository.findByUserIdentity(this.objectiveParam.getUserIdentity()));
		else
			this.setArtifact(OBJECTIVE_LIST, this.accountRepositoryCustomized.searchInAllProperties(this.objectiveParam));
	}
}