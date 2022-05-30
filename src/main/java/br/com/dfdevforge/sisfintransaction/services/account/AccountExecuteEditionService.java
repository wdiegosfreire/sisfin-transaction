package br.com.dfdevforge.sisfintransaction.services.account;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dfdevforge.common.exceptions.BaseException;
import br.com.dfdevforge.common.services.CommonService;
import br.com.dfdevforge.sisfintransaction.entities.AccountEntity;
import br.com.dfdevforge.sisfintransaction.exceptions.DataForEditionNotFoundException;
import br.com.dfdevforge.sisfintransaction.repositories.AccountRepository;

@Service
public class AccountExecuteEditionService extends AccountBaseService implements CommonService {
	@Autowired private AccountRepository accountRepository;

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findById();
		this.editAccount();
		this.findAllAccounts();
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

	private void editAccount() throws BaseException {
		this.accountRepository.save(this.accountParam);
	}

	private void findAllAccounts() {
		this.setArtifact("accountList", this.accountRepository.findByUserIdentityOrderByLevel(this.accountParam.getUserIdentity()));
	}
}