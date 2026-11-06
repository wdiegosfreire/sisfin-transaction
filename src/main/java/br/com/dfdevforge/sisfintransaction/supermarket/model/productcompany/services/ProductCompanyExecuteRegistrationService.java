package br.com.dfdevforge.sisfintransaction.supermarket.model.productcompany.services;

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
import br.com.dfdevforge.sisfintransaction.supermarket.model.productcompany.entities.ProductCompanyEntity;
import br.com.dfdevforge.sisfintransaction.supermarket.model.productcompany.repositories.ProductCompanyRepository;

@Service
@RequestScope
public class ProductCompanyExecuteRegistrationService extends ProductCompanyBaseService implements CommonService {
	private final ProductCompanyRepository productCompanyRepository;

	@Autowired
	public ProductCompanyExecuteRegistrationService(ProductCompanyRepository productCompanyRepository) {
		this.productCompanyRepository = productCompanyRepository;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.checkRequiredFields();

		if (this.productCompanyDoesntExist())
			this.saveProductCompany();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("productCompanyRegistered", this.productCompanyParam);
		return super.returnBusinessData();
	}

	private void checkRequiredFields() throws RequiredFieldNotFoundException {
		List<String> errorList = new ArrayList<>();

		if (StringUtils.isBlank(this.productCompanyParam.getCode()))
			errorList.add("Please, code is required.");
		if (StringUtils.isBlank(this.productCompanyParam.getDescription()))
			errorList.add("Please, description is required.");
		if (this.productCompanyParam.getCompany() == null)
			errorList.add("Please, company is required.");
		if (this.productCompanyParam.getUserIdentity() == null)
			errorList.add("Please, the product company need to be associated with a user.");

		if (!errorList.isEmpty())
			throw new RequiredFieldNotFoundException("Required Field Not Found", errorList);
	}

	/*
	 * Se o produto (codigo) ja estiver cadastrado para a empresa e o usuario, ele e reaproveitado para evitar duplicidade.
	 */
	private boolean productCompanyDoesntExist() {
		ProductCompanyEntity productCompany = this.productCompanyRepository.findFirstByCodeAndCompanyAndUserIdentity(this.productCompanyParam.getCode(), this.productCompanyParam.getCompany(), this.productCompanyParam.getUserIdentity());

		if (productCompany == null)
			return true;

		this.productCompanyParam = productCompany;
		return false;
	}

	private void saveProductCompany() {
		this.productCompanyRepository.save(this.productCompanyParam);
	}
}