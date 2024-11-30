package br.com.dfdevforge.sisfintransaction.statement.model.statementtype.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.statement.model.statementtype.repositories.StatementTypeRepository;

@Service
@RequestScope
@Transactional
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