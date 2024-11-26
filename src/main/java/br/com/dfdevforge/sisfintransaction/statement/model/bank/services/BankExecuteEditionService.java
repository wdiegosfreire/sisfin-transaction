package br.com.dfdevforge.sisfintransaction.statement.model.bank.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.DataForEditionNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.statement.model.bank.entities.BankEntity;
import br.com.dfdevforge.sisfintransaction.statement.model.bank.repositories.BankRepository;

@Service
@RequestScope
@Transactional
public class BankExecuteEditionService extends BankBaseService implements CommonService {
	private final BankRepository bankRepository;

	@Autowired
	public BankExecuteEditionService(BankRepository bankRepository) {
		this.bankRepository = bankRepository;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findById();
		this.editBank();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("bankRegistered", this.bankParam);
		return super.returnBusinessData();
	}

	private void findById() throws DataForEditionNotFoundException {
		BankEntity bank = this.bankRepository.findByIdentity(this.bankParam.getIdentity());

		if (bank == null)
			throw new DataForEditionNotFoundException();
	}

	private void editBank() {
		this.bankRepository.save(this.bankParam);
	}
}