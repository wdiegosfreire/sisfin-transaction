package br.com.dfdevforge.sisfintransaction.supermarket.model.company.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.DataForEditionNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.company.entities.CompanyEntity;
import br.com.dfdevforge.sisfintransaction.supermarket.model.company.repositories.CompanyRepository;

@Service
@RequestScope
public class CompanyExecuteEditionService extends CompanyBaseService implements CommonService {
	private final CompanyRepository companyRepository;

	@Autowired
	public CompanyExecuteEditionService(CompanyRepository companyRepository) {
		this.companyRepository = companyRepository;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findById();
		this.editCompany();
		this.findAllCompanies();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("companyRegistered", this.companyParam);
		return super.returnBusinessData();
	}

	private void findById() throws DataForEditionNotFoundException {
		CompanyEntity company = this.companyRepository.findByIdentity(this.companyParam.getIdentity());

		if (company == null)
			throw new DataForEditionNotFoundException();
	}

	private void editCompany() {
		this.companyRepository.save(this.companyParam);
	}

	private void findAllCompanies() {
		this.setArtifact("companyList", this.companyRepository.findByUserIdentityOrderByNameAsc(companyParam.getUserIdentity()));
	}
}