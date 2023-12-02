package br.com.dfdevforge.sisfintransaction.transaction.services.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.DataForEditionNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.transaction.entities.AccountEntity;
import br.com.dfdevforge.sisfintransaction.transaction.repositories.AccountRepository;

@Service
public class AccountAccessEditionService extends AccountBaseService implements CommonService {
	@Autowired private AccountRepository accountRepository;

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findById();
	}

	private void findById() throws DataForEditionNotFoundException {
		AccountEntity account = this.accountRepository.findByIdentity(this.accountParam.getIdentity());

		if (account == null)
			throw new DataForEditionNotFoundException();

		this.setArtifact("account", account);
	}
}