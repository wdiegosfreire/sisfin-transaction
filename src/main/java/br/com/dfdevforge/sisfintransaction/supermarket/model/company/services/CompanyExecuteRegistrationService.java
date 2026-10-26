package br.com.dfdevforge.sisfintransaction.supermarket.model.company.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.RequiredFieldNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.address.entities.AddressEntity;
import br.com.dfdevforge.sisfintransaction.supermarket.model.address.repositories.AddressRepository;
import br.com.dfdevforge.sisfintransaction.supermarket.model.company.entities.CompanyEntity;
import br.com.dfdevforge.sisfintransaction.supermarket.model.company.repositories.CompanyRepository;

@Service
@RequestScope
@Transactional(rollbackFor = java.lang.Exception.class)
public class CompanyExecuteRegistrationService extends CompanyBaseService implements CommonService {
	private final CompanyRepository companyRepository;
	private final AddressRepository addressRepository;

	@Autowired
	public CompanyExecuteRegistrationService(CompanyRepository companyRepository, AddressRepository addressRepository) {
		this.companyRepository = companyRepository;
		this.addressRepository = addressRepository;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.checkRequiredFields();

		if (!this.reuseCompanyAlreadyRegistered()) {
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

		if (StringUtils.isBlank(this.companyParam.getName()))
			errorList.add("Please, enter name.");
		if (this.companyParam.getUserIdentity() == null)
			errorList.add("Please, the company need to be associated with a user.");

		if (!errorList.isEmpty())
			throw new RequiredFieldNotFoundException("Required Field Not Found", errorList);
	}

	/*
	 * Se a empresa (CNPJ) ja estiver cadastrada para o usuario, ela e reaproveitada para evitar duplicidade.
	 */
	private boolean reuseCompanyAlreadyRegistered() {
		if (StringUtils.isBlank(this.companyParam.getTaxNumber()))
			return false;

		CompanyEntity company = this.companyRepository.findFirstByTaxNumberAndUserIdentity(this.companyParam.getTaxNumber(), this.companyParam.getUserIdentity());

		if (company == null)
			return false;

		this.companyParam = company;
		return true;
	}

	private void saveAddress() {
		AddressEntity address = this.companyParam.getAddress();

		if (address == null)
			return;

		if (address.getUserIdentity() == null)
			address.setUserIdentity(this.companyParam.getUserIdentity());

		this.companyParam.setAddress(this.addressRepository.save(address));
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
