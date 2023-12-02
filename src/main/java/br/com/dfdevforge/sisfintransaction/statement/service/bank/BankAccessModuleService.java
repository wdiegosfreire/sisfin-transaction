package br.com.dfdevforge.sisfintransaction.statement.service.bank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.statement.repositories.BankRepository;

@Service
public class BankAccessModuleService extends BankBaseService implements CommonService {
	@Autowired private BankRepository bankRepository;

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findAllBanks();
	}

	private void findAllBanks() {
		this.setArtifact("bankList", this.bankRepository.findByUserIdentityOrderByNameAsc(bankParam.getUserIdentity()));
	}
}