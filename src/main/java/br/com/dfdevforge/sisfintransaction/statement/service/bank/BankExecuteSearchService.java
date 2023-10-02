package br.com.dfdevforge.sisfintransaction.statement.service.bank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.statement.repositories.BankRepository;
import br.com.dfdevforge.sisfintransaction.statement.repositories.BankRepositoryCustomized;

@Service
public class BankExecuteSearchService extends BankBaseService implements CommonService {
	@Autowired private BankRepository bankRepository;
	@Autowired private BankRepositoryCustomized bankRepositoryCustomized;

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