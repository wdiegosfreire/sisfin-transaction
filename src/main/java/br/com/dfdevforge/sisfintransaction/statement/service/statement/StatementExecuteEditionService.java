package br.com.dfdevforge.sisfintransaction.statement.service.statement;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.DataForEditionNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.statement.entities.StatementEntity;
import br.com.dfdevforge.sisfintransaction.statement.entities.StatementItemEntity;
import br.com.dfdevforge.sisfintransaction.statement.repositories.StatementItemRepository;
import br.com.dfdevforge.sisfintransaction.statement.repositories.StatementRepository;
import br.com.dfdevforge.sisfintransaction.transaction.entities.ObjectiveEntity;
import br.com.dfdevforge.sisfintransaction.transaction.entities.ObjectiveItemEntity;
import br.com.dfdevforge.sisfintransaction.transaction.entities.ObjectiveMovementEntity;
import br.com.dfdevforge.sisfintransaction.transaction.services.objective.ObjectiveExecuteRegistrationService;

@Service
public class StatementExecuteEditionService extends StatementBaseService implements CommonService {
	@Autowired private StatementRepository statementRepository;
	@Autowired private StatementItemRepository statementItemRepository;

	@Autowired private ObjectiveExecuteRegistrationService objectiveExecuteRegistrationService;

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findByIdentity();
		
		this.createMovementBasedOnSatementItem();
		
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

			statementItemLoop.setIsExported(Boolean.TRUE);
			this.statementItemRepository.save(statementItemLoop);
		}
	}

	private void editStatement() {
		this.statementRepository.save(this.statementParam);
	}
}