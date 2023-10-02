package br.com.dfdevforge.sisfintransaction.transaction.services.objective;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.transaction.repositories.ObjectiveRepository;
import br.com.dfdevforge.sisfintransaction.transaction.repositories.ObjectiveRepositoryCustomized;

@Service
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