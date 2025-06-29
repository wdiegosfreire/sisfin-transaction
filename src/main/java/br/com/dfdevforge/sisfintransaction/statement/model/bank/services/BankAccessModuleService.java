package br.com.dfdevforge.sisfintransaction.statement.model.bank.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.statement.model.bank.repositories.BankRepository;

@Service
@RequestScope
@Transactional
public class BankAccessModuleService extends BankBaseService implements CommonService {
	private final BankRepository bankRepository;

	@Autowired
	public BankAccessModuleService(BankRepository bankRepository) {
		this.bankRepository = bankRepository;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findAllBanks();
	}

	private void findAllBanks() {
		this.setArtifact("bankList", this.bankRepository.findByUserIdentityOrderByNameAsc(bankParam.getUserIdentity()));
	}
}