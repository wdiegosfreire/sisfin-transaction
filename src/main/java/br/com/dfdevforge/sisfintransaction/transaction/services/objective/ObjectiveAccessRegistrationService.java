package br.com.dfdevforge.sisfintransaction.transaction.services.objective;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.DataForEditionNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.transaction.entities.AccountEntity;
import br.com.dfdevforge.sisfintransaction.transaction.entities.LocationEntity;
import br.com.dfdevforge.sisfintransaction.transaction.entities.PaymentMethodEntity;
import br.com.dfdevforge.sisfintransaction.transaction.repositories.AccountRepository;
import br.com.dfdevforge.sisfintransaction.transaction.repositories.LocationRepository;
import br.com.dfdevforge.sisfintransaction.transaction.repositories.PaymentMethodRepository;

@Service
public class ObjectiveAccessRegistrationService extends ObjectiveBaseService implements CommonService {
	@Autowired private AccountRepository accountRepository;
	@Autowired private LocationRepository locationRepository;
	@Autowired private PaymentMethodRepository paymentMethodRepository;

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findLocations();
		this.findPaymentMethods();
		this.findAccountsSource();
		this.findAccountsTarget();
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