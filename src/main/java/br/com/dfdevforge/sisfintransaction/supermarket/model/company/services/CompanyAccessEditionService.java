package br.com.dfdevforge.sisfintransaction.supermarket.model.company.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.DataForEditionNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.company.entities.CompanyEntity;
import br.com.dfdevforge.sisfintransaction.supermarket.model.company.repositories.CompanyRepository;

@Service
@RequestScope
@Transactional(rollbackFor = java.lang.Exception.class)
public class CompanyAccessEditionService extends CompanyBaseService implements CommonService {
	private final CompanyRepository companyRepository;

	@Autowired
	public CompanyAccessEditionService(CompanyRepository companyRepository) {
		this.companyRepository = companyRepository;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findById();
	}

	private void findById() throws DataForEditionNotFoundException {
		CompanyEntity company = this.companyRepository.findByIdentity(this.companyParam.getIdentity());

		if (company == null)
			throw new DataForEditionNotFoundException();

		this.setArtifact("company", company);
	}
}