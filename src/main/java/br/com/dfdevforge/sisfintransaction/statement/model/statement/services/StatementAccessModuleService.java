package br.com.dfdevforge.sisfintransaction.statement.model.statement.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.statement.model.statement.entities.StatementEntity;
import br.com.dfdevforge.sisfintransaction.statement.model.statement.repositories.StatementRepository;

@Service
@RequestScope
@Transactional
public class StatementAccessModuleService extends StatementBaseService implements CommonService {
	private final StatementRepository statementRepository;

	@Autowired
	public StatementAccessModuleService(StatementRepository statementRepository) {
		this.statementRepository = statementRepository;
	}

	private List<StatementEntity> statementListResult = new ArrayList<>();

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findAllStatements();
		this.setStatementStatus();
		this.identifyNewHeaderGroup();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("statementList", this.statementListResult);
		return super.returnBusinessData();
	}

	private void findAllStatements() {
		this.statementListResult =  this.statementRepository.findByUserIdentityOrderByYearAscMonthAsc(statementParam.getUserIdentity());
	}

	private void setStatementStatus() {
		this.statementListResult.forEach(statementLoop -> {
			long statementItemsNotExportedCount = statementLoop.getStatementItemList().stream().filter(statementItem -> statementItem.getIsExported() == Boolean.FALSE).count();
			statementLoop.setIsClosed(statementItemsNotExportedCount == 0);
		});
	}

	private void identifyNewHeaderGroup() {
		String headerPeriod = "";
		
		for (StatementEntity statement : this.statementListResult) {
			String checkPeriod = statement.getYear().toString() + statement.getMonth().toString();

			if (!headerPeriod.equals(checkPeriod)) {
				headerPeriod = checkPeriod;
				statement.getProps().setIsNewHeader(Boolean.TRUE);
			}
		}
	}
}