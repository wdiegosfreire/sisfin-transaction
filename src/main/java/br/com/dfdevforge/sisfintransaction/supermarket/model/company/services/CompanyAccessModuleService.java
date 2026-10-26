package br.com.dfdevforge.sisfintransaction.supermarket.model.company.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.company.repositories.CompanyRepository;

@Service
@RequestScope
public class CompanyAccessModuleService extends CompanyBaseService implements CommonService {
	private final CompanyRepository companyRepository;

	@Autowired
	public CompanyAccessModuleService(CompanyRepository companyRepository) {
		this.companyRepository = companyRepository;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findAllCompanies();
	}

	private void findAllCompanies() {
		this.setArtifact("companyList", this.companyRepository.findByUserIdentityOrderByNameAsc(this.companyParam.getUserIdentity()));
	}
}