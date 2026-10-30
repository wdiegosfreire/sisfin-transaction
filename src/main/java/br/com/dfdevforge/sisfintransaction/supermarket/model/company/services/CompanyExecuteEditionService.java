package br.com.dfdevforge.sisfintransaction.supermarket.model.company.services;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.DataForEditionNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.address.entities.AddressEntity;
import br.com.dfdevforge.sisfintransaction.supermarket.model.address.services.AddressExecuteEditionService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.address.services.AddressExecuteRegistrationService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.company.entities.CompanyEntity;
import br.com.dfdevforge.sisfintransaction.supermarket.model.company.repositories.CompanyRepository;

@Service
@RequestScope
public class CompanyExecuteEditionService extends CompanyBaseService implements CommonService {
	private final CompanyRepository companyRepository;
	private final AddressExecuteEditionService addressExecuteEditionService;
	private final AddressExecuteRegistrationService addressExecuteRegistrationService;

	@Autowired
	public CompanyExecuteEditionService(CompanyRepository companyRepository, AddressExecuteEditionService addressExecuteEditionService, AddressExecuteRegistrationService addressExecuteRegistrationService) {
		this.companyRepository = companyRepository;
		this.addressExecuteEditionService = addressExecuteEditionService;
		this.addressExecuteRegistrationService = addressExecuteRegistrationService;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findById();
		this.saveAddress();
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

	private void saveAddress() throws BaseException {
		AddressEntity address = this.companyParam.getAddress();

		if (address == null)
			return;

		address.setUserIdentity(this.companyParam.getUserIdentity());

		if (StringUtils.isBlank(address.getIdentity())) {
			this.addressExecuteRegistrationService.setParams(address);
			this.addressExecuteRegistrationService.execute();
		}
		else {
			this.addressExecuteEditionService.setParams(address);
			this.addressExecuteEditionService.execute();
		}
	}

	private void editCompany() {
		this.companyParam = this.companyRepository.save(this.companyParam);
	}

	private void findAllCompanies() {
		this.setArtifact("companyList", this.companyRepository.findByUserIdentityOrderByNameAsc(companyParam.getUserIdentity()));
	}
}