package br.com.dfdevforge.sisfintransaction.statement.service.statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.statement.repositories.StatementRepository;

@Service
@RequestScope
@Transactional
public class StatementAccessModuleService extends StatementBaseService implements CommonService {
	@Autowired private StatementRepository StatementRepository;

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findAllStatements();
	}

	private void findAllStatements() {
		this.setArtifact("statementList", this.StatementRepository.findByUserIdentityOrderByYearAscMonthAsc(statementParam.getUserIdentity()));
	}
}