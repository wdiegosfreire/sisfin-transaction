package br.com.dfdevforge.sisfintransaction.transaction.model.objective.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.DataForEditionNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.transaction.model.account.entities.AccountEntity;
import br.com.dfdevforge.sisfintransaction.transaction.model.account.repositories.AccountRepository;
import br.com.dfdevforge.sisfintransaction.transaction.model.location.entities.LocationEntity;
import br.com.dfdevforge.sisfintransaction.transaction.model.location.repositories.LocationRepository;
import br.com.dfdevforge.sisfintransaction.transaction.model.objective.entities.ObjectiveEntity;
import br.com.dfdevforge.sisfintransaction.transaction.model.objective.repositories.ObjectiveRepository;
import br.com.dfdevforge.sisfintransaction.transaction.model.objectiveitem.repositories.ObjectiveItemRepository;
import br.com.dfdevforge.sisfintransaction.transaction.model.objectivemovement.repositories.ObjectiveMovementRepository;
import br.com.dfdevforge.sisfintransaction.transaction.model.paymentmethod.entities.PaymentMethodEntity;
import br.com.dfdevforge.sisfintransaction.transaction.model.paymentmethod.repositories.PaymentMethodRepository;

@Service
@RequestScope
@Transactional
public class ObjectiveAccessEditionService extends ObjectiveBaseService implements CommonService {
	private final AccountRepository accountRepository;
	private final LocationRepository locationRepository;
	private final ObjectiveRepository objectiveRepository;
	private final ObjectiveItemRepository objectiveItemRepository;
	private final ObjectiveMovementRepository objectiveMovementRepository;
	private final PaymentMethodRepository paymentMethodRepository;

	@Autowired
	public ObjectiveAccessEditionService(AccountRepository accountRepository, LocationRepository locationRepository, ObjectiveRepository objectiveRepository, ObjectiveItemRepository objectiveItemRepository, ObjectiveMovementRepository objectiveMovementRepository, PaymentMethodRepository paymentMethodRepository) {
		this.accountRepository = accountRepository;
		this.locationRepository = locationRepository;
		this.objectiveRepository = objectiveRepository;
		this.objectiveItemRepository = objectiveItemRepository;
		this.objectiveMovementRepository = objectiveMovementRepository;
		this.paymentMethodRepository = paymentMethodRepository;
	}

	private ObjectiveEntity objectiveResult;

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findObjectiveByIdentity();
		this.findItemsOfObjective();
		this.findMovementsOfObjective();
		this.findLocations();
		this.findPaymentMethods();
		this.findAccountsSource();
		this.findAccountsTarget();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("objective", this.objectiveResult);
		return super.returnBusinessData();
	}

	private void findObjectiveByIdentity() throws DataForEditionNotFoundException {
		this.objectiveResult = this.objectiveRepository.findByIdentity(this.objectiveParam.getIdentity());

		if (objectiveResult == null)
			throw new DataForEditionNotFoundException();
	}

	private void findItemsOfObjective() {
		this.objectiveResult.setObjectiveItemList(objectiveItemRepository.findByObjective(this.objectiveParam));			
	}

	private void findMovementsOfObjective() {
		this.objectiveResult.setObjectiveMovementList(objectiveMovementRepository.findByObjective(this.objectiveParam));			
	}

	private void findLocations() throws DataForEditionNotFoundException {
		List<LocationEntity> locationListCombo = this.locationRepository.findByUserIdentityOrderByNameAsc(this.objectiveParam.getUserIdentity());
		
		if (locationListCombo == null)
			throw new DataForEditionNotFoundException();
		
		this.setArtifact("locationListCombo", locationListCombo);
	}

	private void findPaymentMethods() throws DataForEditionNotFoundException {
		List<PaymentMethodEntity> paymentMethodListCombo = this.paymentMethodRepository.findByUserIdentityOrderByNameAsc(this.objectiveParam.getUserIdentity());
		
		if (paymentMethodListCombo == null)
			throw new DataForEditionNotFoundException();
		
		this.setArtifact("paymentMethodListCombo", paymentMethodListCombo);
	}

	private void findAccountsSource() throws DataForEditionNotFoundException {
		List<AccountEntity> accountListComboSource = this.accountRepository.findByUserIdentityOrderByLevel(this.objectiveParam.getUserIdentity());

		if (accountListComboSource == null)
			throw new DataForEditionNotFoundException();

		this.setArtifact("accountListComboSource", accountListComboSource);
	}

	private void findAccountsTarget() throws DataForEditionNotFoundException {
		List<AccountEntity> accountListComboTarget = this.accountRepository.findByUserIdentityOrderByLevel(this.objectiveParam.getUserIdentity());
		
		if (accountListComboTarget == null)
			throw new DataForEditionNotFoundException();
		
		this.setArtifact("accountListComboTarget", accountListComboTarget);
	}
}