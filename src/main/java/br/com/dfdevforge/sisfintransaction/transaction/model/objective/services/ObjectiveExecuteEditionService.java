package br.com.dfdevforge.sisfintransaction.transaction.model.objective.services;

import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.DataForEditionNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.productcompany.entities.ProductCompanyEntity;
import br.com.dfdevforge.sisfintransaction.supermarket.model.productcompany.services.ProductCompanyExecuteRegistrationService;
import br.com.dfdevforge.sisfintransaction.transaction.model.objective.entities.ObjectiveEntity;
import br.com.dfdevforge.sisfintransaction.transaction.model.objective.repositories.ObjectiveRepository;
import br.com.dfdevforge.sisfintransaction.transaction.model.objectiveitem.entities.ObjectiveItemEntity;
import br.com.dfdevforge.sisfintransaction.transaction.model.objectiveitem.repositories.ObjectiveItemRepository;
import br.com.dfdevforge.sisfintransaction.transaction.model.objectivemovement.entities.ObjectiveMovementEntity;
import br.com.dfdevforge.sisfintransaction.transaction.model.objectivemovement.repositories.ObjectiveMovementRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequestScope
@RequiredArgsConstructor
public class ObjectiveExecuteEditionService extends ObjectiveBaseService implements CommonService {
	private final ObjectiveRepository objectiveRepository;
	private final ObjectiveMovementRepository objectiveMovementRepository;
	private final ObjectiveItemRepository objectiveItemRepository;
	private final ProductCompanyExecuteRegistrationService productCompanyExecuteRegistrationService;

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findByIdentity();
		this.saveObjectiveEdition();
		this.saveObjectiveMovementsEdition();
		this.saveObjectiveItemsEdition();

		if (this.objectiveParam.getProps() != null && this.objectiveParam.getProps().getTaxCouponFile() != null)
			this.registerProductCompaniesFromTaxCoupon();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("objectiveRegistered", this.objectiveParam);
		return super.returnBusinessData();
	}

	private void findByIdentity() throws DataForEditionNotFoundException {
		ObjectiveEntity objective = this.objectiveRepository.findByIdentity(this.objectiveParam.getIdentity());

		if (objective == null)
			throw new DataForEditionNotFoundException();
	}

	private void saveObjectiveEdition() {
		this.objectiveRepository.save(this.objectiveParam);
	}

	private void saveObjectiveMovementsEdition() {
		this.objectiveMovementRepository.deleteAll(this.objectiveMovementRepository.findByObjective(objectiveParam));
		this.objectiveMovementRepository.flush();

		Date now = new Date();
		for (ObjectiveMovementEntity objectiveMovementEdited : this.objectiveParam.getObjectiveMovementList()) {
			objectiveMovementEdited.setIdentity(null);
			objectiveMovementEdited.setRegistrationDate(now);
			objectiveMovementEdited.setObjective(this.objectiveParam);
			objectiveMovementEdited.setUserIdentity(this.objectiveParam.getUserIdentity());
			this.objectiveMovementRepository.save(objectiveMovementEdited);
		}
	}

	private void saveObjectiveItemsEdition() {
		this.objectiveItemRepository.deleteAll(this.objectiveItemRepository.findByObjective(objectiveParam));
		this.objectiveItemRepository.flush();
		
		for (ObjectiveItemEntity objectiveItemEdited : this.objectiveParam.getObjectiveItemList()) {
			objectiveItemEdited.setIdentity(null);
			objectiveItemEdited.setObjective(this.objectiveParam);
			objectiveItemEdited.setUserIdentity(this.objectiveParam.getUserIdentity());
			this.objectiveItemRepository.save(objectiveItemEdited);
		}
	}

	private void registerProductCompaniesFromTaxCoupon() throws BaseException {
		ProductCompanyEntity productCompany = new ProductCompanyEntity();
		productCompany.setUserIdentity(this.objectiveParam.getUserIdentity());
		productCompany.getProps().setTaxCouponFile(this.objectiveParam.getProps().getTaxCouponFile());

		this.productCompanyExecuteRegistrationService.setParams(productCompany);
		this.productCompanyExecuteRegistrationService.execute();
	}
}
