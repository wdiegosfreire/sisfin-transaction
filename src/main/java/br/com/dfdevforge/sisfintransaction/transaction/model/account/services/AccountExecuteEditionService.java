package br.com.dfdevforge.sisfintransaction.transaction.model.account.services;

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

@Service
@RequestScope
@Transactional
public class AccountExecuteEditionService extends AccountBaseService implements CommonService {
	private final AccountRepository accountRepository;

	@Autowired
	public AccountExecuteEditionService(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findById();
		this.editAccount();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("accountRegistered", this.accountParam);
		return super.returnBusinessData();
	}

	private void findById() throws DataForEditionNotFoundException {
		AccountEntity account = this.accountRepository.findByIdentity(this.accountParam.getIdentity());

		if (account == null)
			throw new DataForEditionNotFoundException();
	}

	private void editAccount() {
		this.accountRepository.save(this.accountParam);
	}
}