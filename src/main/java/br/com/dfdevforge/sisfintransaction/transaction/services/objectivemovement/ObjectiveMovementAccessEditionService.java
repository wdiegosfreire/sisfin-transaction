package br.com.dfdevforge.sisfintransaction.transaction.services.objectivemovement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.DataForEditionNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.transaction.entities.AccountEntity;
import br.com.dfdevforge.sisfintransaction.transaction.entities.LocationEntity;
import br.com.dfdevforge.sisfintransaction.transaction.entities.ObjectiveItemEntity;
import br.com.dfdevforge.sisfintransaction.transaction.entities.ObjectiveMovementEntity;
import br.com.dfdevforge.sisfintransaction.transaction.entities.PaymentMethodEntity;
import br.com.dfdevforge.sisfintransaction.transaction.repositories.AccountRepository;
import br.com.dfdevforge.sisfintransaction.transaction.repositories.LocationRepository;
import br.com.dfdevforge.sisfintransaction.transaction.repositories.ObjectiveItemRepository;
import br.com.dfdevforge.sisfintransaction.transaction.repositories.ObjectiveMovementRepository;
import br.com.dfdevforge.sisfintransaction.transaction.repositories.PaymentMethodRepository;

@Service
public class ObjectiveMovementAccessEditionService extends ObjectiveMovementBaseService implements CommonService {
	@Autowired private ObjectiveItemRepository objectiveItemRepository;
	@Autowired private ObjectiveMovementRepository objectiveMovementRepository;
	@Autowired private AccountRepository accountRepository;
	@Autowired private LocationRepository locationRepository;
	@Autowired private PaymentMethodRepository paymentMethodRepository;

	private ObjectiveMovementEntity objectiveMovementResult;

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findById();
		this.findObjectiveItemListByObjective();
		this.findLocations();
		this.findPaymentMethods();
		this.findAccountsSource();
		this.findAccountsTarget();
	}

	private void findById() throws DataForEditionNotFoundException {
		this.objectiveMovementResult = this.objectiveMovementRepository.findByIdentity(this.objectiveMovementParam.getIdentity());

		if (objectiveMovementResult == null)
			throw new DataForEditionNotFoundException();

		this.setArtifact("objectiveMovement", objectiveMovementResult);
	}

	private void findObjectiveItemListByObjective() throws DataForEditionNotFoundException {
		List<ObjectiveItemEntity> objectiveItemList = this.objectiveItemRepository.findByObjective(this.objectiveMovementParam.getObjective());

		if (CollectionUtils.isEmpty(objectiveItemList))
			throw new DataForEditionNotFoundException();

		this.objectiveMovementResult.getObjective().setObjectiveItemList(objectiveItemList);
	}

	private void findLocations() throws DataForEditionNotFoundException {
		List<LocationEntity> locationListCombo = this.locationRepository.findByUserIdentityOrderByNameAsc(this.objectiveMovementParam.getUserIdentity());
		
		if (locationListCombo == null)
			throw new DataForEditionNotFoundException();
		
		this.setArtifact("locationListCombo", locationListCombo);
	}

	private void findPaymentMethods() throws DataForEditionNotFoundException {
		List<PaymentMethodEntity> paymentMethodListCombo = this.paymentMethodRepository.findByUserIdentityOrderByNameAsc(this.objectiveMovementParam.getUserIdentity());
		
		if (paymentMethodListCombo == null)
			throw new DataForEditionNotFoundException();
		
		this.setArtifact("paymentMethodListCombo", paymentMethodListCombo);
	}

	private void findAccountsSource() throws DataForEditionNotFoundException {
		List<AccountEntity> accountListComboSource = this.accountRepository.findByUserIdentityOrderByLevel(this.objectiveMovementParam.getUserIdentity());

		if (accountListComboSource == null)
			throw new DataForEditionNotFoundException();

		this.setArtifact("accountListComboSource", accountListComboSource);
	}

	private void findAccountsTarget() throws DataForEditionNotFoundException {
		List<AccountEntity> accountListComboTarget = this.accountRepository.findByUserIdentityOrderByLevel(this.objectiveMovementParam.getUserIdentity());
		
		if (accountListComboTarget == null)
			throw new DataForEditionNotFoundException();
		
		this.setArtifact("accountListComboTarget", accountListComboTarget);
	}
}