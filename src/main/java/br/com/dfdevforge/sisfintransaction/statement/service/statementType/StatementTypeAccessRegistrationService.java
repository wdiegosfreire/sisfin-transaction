package br.com.dfdevforge.sisfintransaction.statement.service.statementType;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.DataForEditionNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.statement.entities.BankEntity;
import br.com.dfdevforge.sisfintransaction.statement.repositories.BankRepository;

@Service
public class StatementTypeAccessRegistrationService extends StatementTypeService implements CommonService {
	@Autowired private BankRepository bankRepository;

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findBanks();
	}

	private void findBanks() throws DataForEditionNotFoundException {
		List<BankEntity> bankListCombo = this.bankRepository.findByUserIdentityOrderByNameAsc(this.statementTypeParam.getUserIdentity());
		
		if (bankListCombo == null)
			throw new DataForEditionNotFoundException();
		
		this.setArtifact("bankListCombo", bankListCombo);
	}
}