package br.com.dfdevforge.sisfintransaction.transaction.model.objectivemovement.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
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
import br.com.dfdevforge.sisfintransaction.transaction.model.objective.repositories.ObjectiveRepository;
import br.com.dfdevforge.sisfintransaction.transaction.model.objectiveitem.entities.ObjectiveItemEntity;
import br.com.dfdevforge.sisfintransaction.transaction.model.objectiveitem.repositories.ObjectiveItemRepository;
import br.com.dfdevforge.sisfintransaction.transaction.model.objectivemovement.entities.ObjectiveMovementEntity;
import br.com.dfdevforge.sisfintransaction.transaction.model.objectivemovement.repositories.ObjectiveMovementRepository;
import br.com.dfdevforge.sisfintransaction.transaction.model.objectivemovement.repositories.ObjectiveMovementRepositoryCustomized;

@Service
@RequestScope
@Transactional
public class ObjectiveMovementExecuteRegistrationService extends ObjectiveMovementBaseService implements CommonService {
	private BigDecimal installmentValue;
	private BigDecimal installmentDiference;

	private final ObjectiveRepository objectiveRepository;
	private final ObjectiveItemRepository objectiveItemRepository;
	private final ObjectiveMovementRepository objectiveMovementRepository;
	private final ObjectiveMovementRepositoryCustomized objectiveMovementRepositoryCustomized;

	@Autowired
	public ObjectiveMovementExecuteRegistrationService(ObjectiveRepository objectiveRepository, ObjectiveItemRepository objectiveItemRepository, ObjectiveMovementRepository objectiveMovementRepository, ObjectiveMovementRepositoryCustomized objectiveMovementRepositoryCustomized) {
		this.objectiveRepository = objectiveRepository;
		this.objectiveItemRepository = objectiveItemRepository;
		this.objectiveMovementRepository = objectiveMovementRepository;
		this.objectiveMovementRepositoryCustomized = objectiveMovementRepositoryCustomized;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.fillDefaultValues();
		this.checkRequiredFields();
		this.saveObjective();
		this.calculateInstallmentValue();
		this.calculateInstallmentDiference();
		this.saveObjectiveMovement();
		this.saveObjectiveItem();
		this.findAllObjectiveMovements();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("objectiveMovementRegistered", this.objectiveMovementParam);
		return super.returnBusinessData();
	}

	private void fillDefaultValues() {
		this.objectiveMovementParam.setRegistrationDate(new Date());
		this.objectiveMovementParam.getObjective().setUserIdentity(this.objectiveMovementParam.getUserIdentity());
	}

	private void checkRequiredFields() throws RequiredFieldNotFoundException {
		List<String> errorList = new ArrayList<>();

		if (this.objectiveMovementParam.getRegistrationDate() == null)
			errorList.add("Please, enter registration date.");

		if (this.objectiveMovementParam.getDueDate() == null)
			errorList.add("Please, enter due date.");

		if (this.objectiveMovementParam.getAccountSource() == null || this.objectiveMovementParam.getAccountSource().getIdentity() == null)
			errorList.add("Please, enter source account.");

		if (this.objectiveMovementParam.getUserIdentity() == null)
			errorList.add("Please, the movement need to be associated with a user.");

		if (!errorList.isEmpty())
			throw new RequiredFieldNotFoundException("Required Field Not Found", errorList);
	}

	private void saveObjective() {
		this.objectiveRepository.save(this.objectiveMovementParam.getObjective());
	}

	private void calculateInstallmentValue() {
		BigDecimal totalValue = this.objectiveMovementParam.getValue();
		BigDecimal installmentNumber = new BigDecimal(this.objectiveMovementParam.getInstallment());
		
		this.installmentValue = totalValue.divide(installmentNumber, RoundingMode.HALF_UP);
	}

	private void calculateInstallmentDiference() {
		BigDecimal totalValue = this.objectiveMovementParam.getValue();
		BigDecimal installmentNumber = new BigDecimal(this.objectiveMovementParam.getInstallment());

		this.installmentDiference = totalValue.subtract(installmentValue.multiply(installmentNumber));
		if (installmentValue.multiply(installmentNumber).compareTo(totalValue) > 0) {
			installmentDiference = installmentValue.multiply(installmentNumber).subtract(totalValue);
			installmentDiference = installmentDiference.multiply(new BigDecimal(-1));
		}
	}

	private void saveObjectiveMovement() {
		for (int i = 0; i < this.objectiveMovementParam.getInstallment(); i++) {
			ObjectiveMovementEntity objectiveMovementNew = new ObjectiveMovementEntity();
			objectiveMovementNew.setRegistrationDate(this.objectiveMovementParam.getRegistrationDate());
			objectiveMovementNew.setDueDate(Utils.date.plusMonths(this.objectiveMovementParam.getDueDate(), i));

			if (i == 0)
				objectiveMovementNew.setPaymentDate(this.objectiveMovementParam.getPaymentDate());

			objectiveMovementNew.setValue(installmentValue);
			if (this.objectiveMovementParam.getInstallment() - i == 1)
				objectiveMovementNew.setValue(installmentValue.add(installmentDiference));

			objectiveMovementNew.setInstallment(i + 1);
			objectiveMovementNew.setObjective(this.objectiveMovementParam.getObjective());
			objectiveMovementNew.setPaymentMethod(this.objectiveMovementParam.getPaymentMethod());
			objectiveMovementNew.setAccountSource(this.objectiveMovementParam.getAccountSource());
			objectiveMovementNew.setUserIdentity(this.objectiveMovementParam.getUserIdentity());

			this.objectiveMovementRepository.save(objectiveMovementNew);
		}
	}

	private void saveObjectiveItem() {
		for (ObjectiveItemEntity objectiveItemLoop : this.objectiveMovementParam.getObjective().getObjectiveItemList()) {
			ObjectiveItemEntity objectiveItemNew = new ObjectiveItemEntity();
			objectiveItemNew.setDescription(objectiveItemLoop.getDescription());
			objectiveItemNew.setSequential(objectiveItemLoop.getSequential());
			objectiveItemNew.setUnitaryValue(objectiveItemLoop.getUnitaryValue());
			objectiveItemNew.setAmount(objectiveItemLoop.getAmount());
			objectiveItemNew.setObjective(this.objectiveMovementParam.getObjective());
			objectiveItemNew.setAccountTarget(objectiveItemLoop.getAccountTarget());
			objectiveItemNew.setUserIdentity(objectiveItemLoop.getUserIdentity());

			this.objectiveItemRepository.save(objectiveItemNew);
		}
	}

	private void findAllObjectiveMovements() {
		this.setArtifact("objectiveMovementList", this.objectiveMovementRepositoryCustomized.searchByPeriod(this.objectiveMovementParam.getPaymentDate(), this.objectiveMovementParam.getUserIdentity()));
	}
}