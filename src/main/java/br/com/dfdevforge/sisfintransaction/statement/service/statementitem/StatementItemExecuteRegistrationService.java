package br.com.dfdevforge.sisfintransaction.statement.service.statementitem;

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
import br.com.dfdevforge.sisfintransaction.commons.utils.Utils;
import br.com.dfdevforge.sisfintransaction.statement.repositories.StatementItemRepository;

@Service
@RequestScope
@Transactional
public class StatementItemExecuteRegistrationService extends StatementItemBaseService implements CommonService {
	private final StatementItemRepository statementItemRepository;

	@Autowired
	public StatementItemExecuteRegistrationService(StatementItemRepository statementItemRepository) {
		this.statementItemRepository = statementItemRepository;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.checkRequiredFields();
		this.saveStatementItem();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("statementItemRegistered", this.statementItemParam);
		return super.returnBusinessData();
	}

	private void checkRequiredFields() throws RequiredFieldNotFoundException {
		List<String> errorList = new ArrayList<>();

		if (this.statementItemParam.getMovementDate() == null)
			errorList.add("Please, enter item movement date.");
		if (Utils.value.notExists(this.statementItemParam.getDescription()))
			errorList.add("Please, enter item description.");
		if (Utils.value.notExists(this.statementItemParam.getOperationType()))
			errorList.add("Please, enter item operation type.");
		if (this.statementItemParam.getMovementValue() == null)
			errorList.add("Please, enter item movement value.");
		if (this.statementItemParam.getStatement() == null)
			errorList.add("Please, enter item statement.");
		if (this.statementItemParam.getUserIdentity() == null)
			errorList.add("Please, the statement item needs to be associated with a user.");

		if (!errorList.isEmpty())
			throw new RequiredFieldNotFoundException("Required Field Not Found", errorList);
	}

	private void saveStatementItem() {
		this.statementItemRepository.save(this.statementItemParam);
	}
}