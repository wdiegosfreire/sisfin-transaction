package br.com.dfdevforge.sisfintransaction.supermarket.model.company.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.DataForExclusionNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.company.entities.CompanyEntity;
import br.com.dfdevforge.sisfintransaction.supermarket.model.company.repositories.CompanyRepository;

@Service
@RequestScope
public class CompanyExecuteExclusionService extends CompanyBaseService implements CommonService {
	private final CompanyRepository companyRepository;

	@Autowired
	public CompanyExecuteExclusionService(CompanyRepository companyRepository) {
		this.companyRepository = companyRepository;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findById();
		this.deleteCompany();
		this.findAllCompanies();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("companyRegistered", this.companyParam);
		return super.returnBusinessData();
	}

	private void findById() throws DataForExclusionNotFoundException {
		CompanyEntity company = this.companyRepository.findByIdentity(this.companyParam.getIdentity());

		if (company == null)
			throw new DataForExclusionNotFoundException();
	}

	private void deleteCompany() {
		this.companyRepository.delete(this.companyParam);
	}

	private void findAllCompanies() {
		this.setArtifact("companyList", this.companyRepository.findByUserIdentityOrderByNameAsc(companyParam.getUserIdentity()));
	}
}