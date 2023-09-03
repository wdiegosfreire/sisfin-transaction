package br.com.dfdevforge.sisfintransaction.services.objective;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dfdevforge.common.exceptions.BaseException;
import br.com.dfdevforge.common.services.CommonService;
import br.com.dfdevforge.sisfintransaction.entities.AccountEntity;
import br.com.dfdevforge.sisfintransaction.entities.LocationEntity;
import br.com.dfdevforge.sisfintransaction.entities.ObjectiveEntity;
import br.com.dfdevforge.sisfintransaction.entities.PaymentMethodEntity;
import br.com.dfdevforge.sisfintransaction.exceptions.DataForEditionNotFoundException;
import br.com.dfdevforge.sisfintransaction.repositories.AccountRepository;
import br.com.dfdevforge.sisfintransaction.repositories.LocationRepository;
import br.com.dfdevforge.sisfintransaction.repositories.ObjectiveItemRepository;
import br.com.dfdevforge.sisfintransaction.repositories.ObjectiveMovementRepository;
import br.com.dfdevforge.sisfintransaction.repositories.ObjectiveRepository;
import br.com.dfdevforge.sisfintransaction.repositories.PaymentMethodRepository;

@Service
public class ObjectiveAccessEditionService extends ObjectiveBaseService implements CommonService {
	@Autowired private ObjectiveRepository objectiveRepository;
	@Autowired private ObjectiveItemRepository objectiveItemRepository;
	@Autowired private ObjectiveMovementRepository objectiveMovementRepository;

	@Autowired private AccountRepository accountRepository;
	@Autowired private LocationRepository locationRepository;
	@Autowired private PaymentMethodRepository paymentMethodRepository;

	private ObjectiveEntity objectiveResult;

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findByIdentity();
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

	private void findByIdentity() throws DataForEditionNotFoundException {
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
		List<LocationEntity> locationListCombo = this.locationRepository.findByUserIdentityOrderByNameAscBranchAsc(this.objectiveParam.getUserIdentity());
		
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