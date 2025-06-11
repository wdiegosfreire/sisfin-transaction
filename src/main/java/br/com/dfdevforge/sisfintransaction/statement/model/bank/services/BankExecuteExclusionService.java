package br.com.dfdevforge.sisfintransaction.statement.model.bank.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.DataForExclusionNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.statement.model.bank.entities.BankEntity;
import br.com.dfdevforge.sisfintransaction.statement.model.bank.repositories.BankRepository;

@Service
@RequestScope
@Transactional
public class BankExecuteExclusionService extends BankBaseService implements CommonService {
	private final BankRepository bankRepository;

	@Autowired
	public BankExecuteExclusionService(BankRepository bankRepository) {
		this.bankRepository = bankRepository;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findById();
		this.deleteBank();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("bankRegistered", this.bankParam);
		return super.returnBusinessData();
	}

	private void findById() throws DataForExclusionNotFoundException {
		BankEntity bank = this.bankRepository.findByIdentity(this.bankParam.getIdentity());

		if (bank == null)
			throw new DataForExclusionNotFoundException();
	}

	private void deleteBank() {
		this.bankRepository.delete(this.bankParam);
	}
}