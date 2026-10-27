package br.com.dfdevforge.sisfintransaction.supermarket.model.company.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.RequiredFieldNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.address.services.AddressExecuteRegistrationService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.company.entities.CompanyEntity;
import br.com.dfdevforge.sisfintransaction.supermarket.model.company.repositories.CompanyRepository;

@Service
@RequestScope
public class CompanyExecuteRegistrationService extends CompanyBaseService implements CommonService {
	private final CompanyRepository companyRepository;
	private final AddressExecuteRegistrationService addressExecuteRegistrationService;

	@Autowired
	public CompanyExecuteRegistrationService(CompanyRepository companyRepository, AddressExecuteRegistrationService addressExecuteRegistrationService) {
		this.companyRepository = companyRepository;
		this.addressExecuteRegistrationService = addressExecuteRegistrationService;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.checkRequiredFields();

		if (this.companyDoesntExist()) {
			this.saveAddress();
			this.saveCompany();
		}

		this.findAllCompanies();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("companyRegistered", this.companyParam);
		return super.returnBusinessData();
	}

	private void checkRequiredFields() throws RequiredFieldNotFoundException {
		List<String> errorList = new ArrayList<>();

		if (StringUtils.isBlank(this.companyParam.getTaxNumber()))
			errorList.add("Tax number is required.");
		if (StringUtils.isBlank(this.companyParam.getName()))
			errorList.add("Company name is required.");
		if (this.companyParam.getUserIdentity() == null)
			errorList.add("Please, the company need to be associated with a user.");

		if (!errorList.isEmpty())
			throw new RequiredFieldNotFoundException("Required Field Not Found", errorList);
	}

	/*
	 * Se a empresa (CNPJ) ja estiver cadastrada para o usuario, ela e reaproveitada para evitar duplicidade.
	 */
	private boolean companyDoesntExist() {
		CompanyEntity company = this.companyRepository.findFirstByTaxNumberAndUserIdentity(this.companyParam.getTaxNumber(), this.companyParam.getUserIdentity());

		if (company == null)
			return true;

		this.companyParam = company;
		return false;
	}

	private void saveAddress() throws BaseException {
		this.addressExecuteRegistrationService.setParams(this.companyParam.getAddress());
		this.addressExecuteRegistrationService.execute();
	}

	private void saveCompany() {
		if (this.companyParam.getIsInactive() == null)
			this.companyParam.setIsInactive(false);

		this.companyParam = this.companyRepository.save(this.companyParam);
	}

	private void findAllCompanies() {
		this.setArtifact("companyList", this.companyRepository.findByUserIdentityOrderByNameAsc(companyParam.getUserIdentity()));
	}
}
