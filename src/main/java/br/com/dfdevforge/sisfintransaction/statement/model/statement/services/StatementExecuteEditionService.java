package br.com.dfdevforge.sisfintransaction.statement.model.statement.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.DataForEditionNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.statement.model.statement.entities.StatementEntity;
import br.com.dfdevforge.sisfintransaction.statement.model.statement.repositories.StatementRepository;
import br.com.dfdevforge.sisfintransaction.statement.model.statementitem.entities.StatementItemEntity;
import br.com.dfdevforge.sisfintransaction.statement.model.statementitem.repositories.StatementItemRepository;
import br.com.dfdevforge.sisfintransaction.transaction.model.objective.entities.ObjectiveEntity;
import br.com.dfdevforge.sisfintransaction.transaction.model.objective.services.ObjectiveExecuteRegistrationService;
import br.com.dfdevforge.sisfintransaction.transaction.model.objectiveitem.entities.ObjectiveItemEntity;
import br.com.dfdevforge.sisfintransaction.transaction.model.objectivemovement.entities.ObjectiveMovementEntity;

@Service
@RequestScope
@Transactional
public class StatementExecuteEditionService extends StatementBaseService implements CommonService {
	private final StatementRepository statementRepository;
	private final StatementItemRepository statementItemRepository;
	private final ObjectiveExecuteRegistrationService objectiveExecuteRegistrationService;

	@Autowired
	public StatementExecuteEditionService(StatementRepository statementRepository, StatementItemRepository statementItemRepository, ObjectiveExecuteRegistrationService objectiveExecuteRegistrationService) {
		this.statementRepository = statementRepository;
		this.statementItemRepository = statementItemRepository;
		this.objectiveExecuteRegistrationService = objectiveExecuteRegistrationService;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findByIdentity();

		if (this.statementParam.getIsCreateMovement().booleanValue())
			this.createMovementBasedOnSatementItem();

		this.setStatementItemAsExported();
		this.editStatement();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("statementRegistered", this.statementParam);
		return super.returnBusinessData();
	}

	private void findByIdentity() throws DataForEditionNotFoundException {
		StatementEntity statement = this.statementRepository.findByIdentity(this.statementParam.getIdentity());

		if (statement == null)
			throw new DataForEditionNotFoundException();

		this.statementParam.setStatementType(statement.getStatementType());

		for (StatementItemEntity statementItemLoop : this.statementParam.getStatementItemList()) {
			statementItemLoop.setStatement(new StatementEntity());
			statementItemLoop.getStatement().setIdentity(statement.getIdentity());
		}
	}

	private void createMovementBasedOnSatementItem() throws BaseException {
		for (StatementItemEntity statementItemLoop : this.statementParam.getStatementItemList()) {
			String description = StringUtils.isBlank(statementItemLoop.getDescriptionNew()) ? statementItemLoop.getDescription() : statementItemLoop.getDescriptionNew();

			ObjectiveEntity objective = new ObjectiveEntity();
			objective.setObjectiveMovementList(new ArrayList<>());
			objective.setObjectiveItemList(new ArrayList<>());
			objective.setDescription(description);
			objective.setLocation(statementItemLoop.getLocation());
			objective.setUserIdentity(statementItemLoop.getUserIdentity());

			ObjectiveMovementEntity objectiveMovement= new ObjectiveMovementEntity();
			objectiveMovement.setDueDate(statementItemLoop.getMovementDate());
			objectiveMovement.setPaymentDate(statementItemLoop.getMovementDate());
			objectiveMovement.setValue(statementItemLoop.getMovementValue());
			objectiveMovement.setInstallment(1);
			objectiveMovement.setPaymentMethod(statementItemLoop.getPaymentMethod());
			objectiveMovement.setAccountSource(statementItemLoop.isOutcoming() ? this.statementParam.getStatementType().getAccountSource() : statementItemLoop.getAccountSource());
			
			ObjectiveItemEntity objectiveItem = new ObjectiveItemEntity();
			objectiveItem.setDescription(description);
			objectiveItem.setSequential(1);
			objectiveItem.setUnitaryValue(statementItemLoop.getMovementValue());
			objectiveItem.setAmount(new BigDecimal(1));
			objectiveItem.setAccountTarget(statementItemLoop.isIncoming() ? this.statementParam.getStatementType().getAccountSource() : statementItemLoop.getAccountTarget());

			objective.getObjectiveMovementList().add(objectiveMovement);
			objective.getObjectiveItemList().add(objectiveItem);

			this.objectiveExecuteRegistrationService.setParams(objective, token);
			this.objectiveExecuteRegistrationService.execute();
		}
	}

	private void setStatementItemAsExported() {
		for (StatementItemEntity statementItemLoop : this.statementParam.getStatementItemList()) {
			statementItemLoop.setIsExported(Boolean.TRUE);
			this.statementItemRepository.save(statementItemLoop);
		}
	}

	private void editStatement() {
		this.statementRepository.save(this.statementParam);
	}
}