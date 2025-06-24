package br.com.dfdevforge.sisfintransaction.statement.model.bank.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.statement.model.bank.repositories.BankRepository;
import br.com.dfdevforge.sisfintransaction.statement.model.bank.repositories.BankRepositoryCustomized;

@Service
@RequestScope
@Transactional
public class BankExecuteSearchService extends BankBaseService implements CommonService {
	private final BankRepository bankRepository;
	private final BankRepositoryCustomized bankRepositoryCustomized;

	@Autowired
	public BankExecuteSearchService(BankRepository bankRepository, BankRepositoryCustomized bankRepositoryCustomized) {
		this.bankRepository = bankRepository;
		this.bankRepositoryCustomized = bankRepositoryCustomized;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findAllBanks();
	}

	private void findAllBanks() {
		if (this.bankParam.getFilter() == null || this.bankParam.getFilter().contentEquals(""))
			this.setArtifact("bankList", this.bankRepository.findByUserIdentityOrderByNameAsc(bankParam.getUserIdentity()));
		else
			this.setArtifact("bankList", this.bankRepositoryCustomized.searchInAllProperties(this.bankParam));
	}
}