package br.com.dfdevforge.sisfintransaction.statement.service.bank;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.DataForExclusionNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.statement.entities.BankEntity;
import br.com.dfdevforge.sisfintransaction.statement.repositories.BankRepository;

@Service
public class BankExecuteExclusionService extends BankBaseService implements CommonService {
	@Autowired private BankRepository bankRepository;

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

	private void deleteBank() throws BaseException {
		this.bankRepository.delete(this.bankParam);
	}
}