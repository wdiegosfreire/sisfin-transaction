package br.com.dfdevforge.sisfintransaction.statement.service.statement;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.DataForEditionNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.statement.entities.StatementEntity;
import br.com.dfdevforge.sisfintransaction.statement.repositories.StatementItemRepository;
import br.com.dfdevforge.sisfintransaction.statement.repositories.StatementRepository;
import br.com.dfdevforge.sisfintransaction.transaction.repositories.LocationRepository;

@Service
@RequestScope
@Transactional
public class StatementAccessEditionService extends StatementBaseService implements CommonService {
	private final LocationRepository locationRepository;
	private final StatementRepository statementRepository;
	private final StatementItemRepository statementItemRepository;

	@Autowired
	public StatementAccessEditionService(LocationRepository locationRepository, StatementRepository statementRepository, StatementItemRepository statementItemRepository) {
		this.locationRepository = locationRepository;
		this.statementRepository = statementRepository;
		this.statementItemRepository = statementItemRepository;
	}

	private StatementEntity statementResult;

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findByIdentity();
		this.findItemsOfStatement();
		this.findLocations();
		this.findAccountsSource();
		this.findAccountsTarget();
		this.findPaymentMethods();
		this.setStatementStatus();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("statement", this.statementResult);
		return super.returnBusinessData();
	}

	private void findByIdentity() throws DataForEditionNotFoundException {
		this.statementResult = this.statementRepository.findByIdentity(this.statementParam.getIdentity());

		if (statementResult == null)
			throw new DataForEditionNotFoundException();
	}

	private void findItemsOfStatement() {
		this.statementResult.setStatementItemList(statementItemRepository.findByStatementOrderByMovementDateAscIdentityAsc(this.statementParam));			
	}

	private void findLocations() {
		this.setArtifact("locationListCombo", this.locationRepository.findByUserIdentityOrderByNameAsc(this.statementParam.getUserIdentity()));
	}

	private void findAccountsSource() {
		this.setArtifact("accountListComboSource", this.findAccountsByUserIdentityOrderByLevel(this.statementParam.getUserIdentity()));
	}

	private void findAccountsTarget() {
		this.setArtifact("accountListComboTarget", this.findAccountsByUserIdentityOrderByLevel(this.statementParam.getUserIdentity()));
	}

	private void findPaymentMethods() {
		this.setArtifact("paymentMethodListCombo", this.findPaymentMethodsByUserIdentityOrderByNameAsc(this.statementParam.getUserIdentity()));
	}

	private void setStatementStatus() {
		long statementItemsNotExportedCount = this.statementResult.getStatementItemList().stream().filter(statementItem -> statementItem.getIsExported() == Boolean.FALSE).count();
		this.statementResult.setIsClosed(statementItemsNotExportedCount == 0);
	}
}