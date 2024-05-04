package br.com.dfdevforge.sisfintransaction.statement.service.statementpattern;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.RequiredFieldNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.statement.repositories.StatementPatternRepository;

@Service
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
		if (this.statementPatternParam.getUserIdentity() == null)
			errorList.add("Please, the bank need to be associated with a user.");

		if (!errorList.isEmpty())
			throw new RequiredFieldNotFoundException("Required Field Not Found", errorList);
	}

	private void saveStatementPattern() {
		this.statementPatternRepository.save(this.statementPatternParam);
	}
}