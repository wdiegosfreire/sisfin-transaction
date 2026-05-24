package br.com.dfdevforge.sisfintransaction.statement.model.statement.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
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
import br.com.dfdevforge.sisfintransaction.transaction.model.objective.repositories.ObjectiveRepository;
import br.com.dfdevforge.sisfintransaction.transaction.model.objective.services.ObjectiveExecuteRegistrationService;
import br.com.dfdevforge.sisfintransaction.transaction.model.objectiveitem.entities.ObjectiveItemEntity;
import br.com.dfdevforge.sisfintransaction.transaction.model.objectiveitem.repositories.ObjectiveItemRepository;
import br.com.dfdevforge.sisfintransaction.transaction.model.objectivemovement.entities.ObjectiveMovementEntity;
import br.com.dfdevforge.sisfintransaction.transaction.model.objectivemovement.repositories.ObjectiveMovementRepository;

@Service
@RequestScope
@Transactional
public class StatementExecuteEditionService extends StatementBaseService implements CommonService {
	private final ObjectiveRepository objectiveRepository;
	private final ObjectiveItemRepository objectiveItemRepository;
	private final ObjectiveMovementRepository objectiveMovementRepository;

	private final StatementRepository statementRepository;
	private final StatementItemRepository statementItemRepository;
	private final ObjectiveExecuteRegistrationService objectiveExecuteRegistrationService;

	@Autowired
	public StatementExecuteEditionService(
		ObjectiveRepository objectiveRepository,
		ObjectiveItemRepository objectiveItemRepository,
		ObjectiveMovementRepository objectiveMovementRepository,
		StatementRepository statementRepository,
		StatementItemRepository statementItemRepository,
		ObjectiveExecuteRegistrationService objectiveExecuteRegistrationService) {

		this.objectiveRepository = objectiveRepository;
		this.objectiveItemRepository = objectiveItemRepository;
		this.objectiveMovementRepository = objectiveMovementRepository;

		this.statementRepository = statementRepository;
		this.statementItemRepository = statementItemRepository;
		this.objectiveExecuteRegistrationService = objectiveExecuteRegistrationService;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findByIdentity();

		if (this.statementParam.getProps().getIsCreateObjective().booleanValue())
			this.createFullObjective();
		else if (this.statementParam.getProps().getIsAddInstallment().booleanValue())
			this.createInstallmentInExistingObjective();

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

	private void createFullObjective() throws BaseException {
		for (StatementItemEntity statementItemLoop : this.statementParam.getStatementItemList()) {
			String description = StringUtils.isBlank(statementItemLoop.getDescriptionNew()) ? statementItemLoop.getDescription() : statementItemLoop.getDescriptionNew();

			ObjectiveEntity objective = new ObjectiveEntity();
			objective.setObjectiveMovementList(new ArrayList<>());
			objective.setObjectiveItemList(new ArrayList<>());
			objective.setDescription(description);
			objective.setInstallmentAmount(statementItemLoop.getProps().getInstallmentAmount());
			objective.setLocation(statementItemLoop.getLocation());
			objective.setUserIdentity(statementItemLoop.getUserIdentity());

			ObjectiveMovementEntity objectiveMovement= new ObjectiveMovementEntity();
			objectiveMovement.setDueDate(statementItemLoop.getMovementDate());
			objectiveMovement.setPaymentDate(statementItemLoop.getMovementDate());
			objectiveMovement.setValue(statementItemLoop.getMovementValue());
			objectiveMovement.setInstallment(1);
			objectiveMovement.setPaymentMethod(statementItemLoop.getPaymentMethod());
			objectiveMovement.setAccountSource(statementItemLoop.methods.isOutcoming() ? this.statementParam.getStatementType().getAccountSource() : statementItemLoop.getAccountSource());

			ObjectiveItemEntity objectiveItem = new ObjectiveItemEntity();
			objectiveItem.setDescription(description);
			objectiveItem.setSequential(1);
			objectiveItem.setUnitaryValue(statementItemLoop.getMovementValue());
			objectiveItem.setAmount(new BigDecimal(1));
			objectiveItem.setAccountTarget(statementItemLoop.methods.isIncoming() ? this.statementParam.getStatementType().getAccountSource() : statementItemLoop.getAccountTarget());

			objective.getObjectiveMovementList().add(objectiveMovement);
			objective.getObjectiveItemList().add(objectiveItem);

			this.objectiveExecuteRegistrationService.setParams(objective, token);
			this.objectiveExecuteRegistrationService.execute();
		}
	}

	private void createInstallmentInExistingObjective() throws DataForEditionNotFoundException {
		StatementItemEntity statementItem = this.statementParam.getStatementItemList().get(0);
		ObjectiveEntity objective = this.objectiveRepository.findByIdentity(this.statementParam.getProps().getObjectiveIdentity());

		List<ObjectiveItemEntity> objectiveItemList = this.objectiveItemRepository.findByObjective(objective);
		objectiveItemList.forEach(item -> item.setUnitaryValue(item.getUnitaryValue().add(statementItem.getMovementValue())));
		this.objectiveItemRepository.saveAll(objectiveItemList);

		List<ObjectiveMovementEntity> objectiveMovementList = this.objectiveMovementRepository.findByObjective(objective);
		ObjectiveMovementEntity objectiveMovementLastInstallment = objectiveMovementList.stream().max(Comparator.comparing(ObjectiveMovementEntity::getInstallment)).orElse(null);

		if (objectiveMovementLastInstallment == null || objectiveMovementLastInstallment.getInstallment().equals(objective.getInstallmentAmount()))
			throw new DataForEditionNotFoundException();

		ObjectiveMovementEntity objectiveMovement= new ObjectiveMovementEntity();
		objectiveMovement.setDueDate(statementItem.getMovementDate());
		objectiveMovement.setPaymentDate(statementItem.getMovementDate());
		objectiveMovement.setValue(statementItem.getMovementValue());
		objectiveMovement.setRegistrationDate(Calendar.getInstance().getTime());
		objectiveMovement.setInstallment(objectiveMovementLastInstallment.getInstallment() + 1);
		objectiveMovement.setPaymentMethod(objectiveMovementLastInstallment.getPaymentMethod());
		objectiveMovement.setAccountSource(objectiveMovementLastInstallment.getAccountSource());
		objectiveMovement.setUserIdentity(this.statementParam.getUserIdentity());
		objectiveMovement.setObjective(objective);
		this.objectiveMovementRepository.save(objectiveMovement);
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