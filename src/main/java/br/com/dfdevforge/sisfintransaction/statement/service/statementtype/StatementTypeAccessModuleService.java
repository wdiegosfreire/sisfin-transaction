package br.com.dfdevforge.sisfintransaction.statement.service.statementtype;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.statement.repositories.StatementTypeRepository;

@Service
public class StatementTypeAccessModuleService extends StatementTypeBaseService implements CommonService {
	private final StatementTypeRepository statementTypeRepository;

	@Autowired
	public StatementTypeAccessModuleService(StatementTypeRepository statementTypeRepository) {
		this.statementTypeRepository = statementTypeRepository;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findAllStatementTypes();
	}

	private void findAllStatementTypes() {
		this.setArtifact("statementTypeList", this.statementTypeRepository.findByUserIdentityOrderByNameAsc(statementTypeParam.getUserIdentity()));
	}
}