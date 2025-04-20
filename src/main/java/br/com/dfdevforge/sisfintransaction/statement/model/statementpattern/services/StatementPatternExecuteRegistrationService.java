package br.com.dfdevforge.sisfintransaction.statement.model.statementpattern.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.RequiredFieldNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.statement.model.statementpattern.repositories.StatementPatternRepository;

@Service
@RequestScope
@Transactional
public class StatementPatternExecuteRegistrationService extends StatementPatternBaseService implements CommonService {
	private final StatementPatternRepository statementPatternRepository;

	@Autowired
	public StatementPatternExecuteRegistrationService(StatementPatternRepository statementPatternRepository) {
		this.statementPatternRepository = statementPatternRepository;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.checkRequiredFields();
		this.saveStatementPattern();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("statementPatternRegistered", this.statementPatternParam);
		return super.returnBusinessData();
	}

	private void checkRequiredFields() throws RequiredFieldNotFoundException {
		List<String> errorList = new ArrayList<>();

		if (this.statementPatternParam.getComparator() == null || this.statementPatternParam.getComparator().equals(""))
			errorList.add("Please, enter name.");
		if (this.statementPatternParam.getAccountSource() == null || this.statementPatternParam.getAccountSource().getIdentity() == null)
			errorList.add("Please, enter source account.");
		if (this.statementPatternParam.getAccountTarget() == null || this.statementPatternParam.getAccountTarget().getIdentity() == null)
			errorList.add("Please, enter target account.");
		if (this.statementPatternParam.getPaymentMethod() == null || this.statementPatternParam.getPaymentMethod().getIdentity() == null)
			errorList.add("Please, enter payment method.");
		if (this.statementPatternParam.getUserIdentity() == null)
			errorList.add("Please, the statement pattern need to be associated with a user.");

		if (!errorList.isEmpty())
			throw new RequiredFieldNotFoundException("Required Field Not Found", errorList);
	}

	private void saveStatementPattern() {
		this.statementPatternRepository.save(this.statementPatternParam);
	}
}