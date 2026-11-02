package br.com.dfdevforge.sisfintransaction.supermarket.model.productproperty.services;

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
import br.com.dfdevforge.sisfintransaction.supermarket.model.productproperty.repositories.ProductPropertyRepository;

@Service
@RequestScope
public class ProductPropertyExecuteRegistrationService extends ProductPropertyBaseService implements CommonService {
	private final ProductPropertyRepository productPropertyRepository;

	@Autowired
	public ProductPropertyExecuteRegistrationService(ProductPropertyRepository productPropertyRepository) {
		this.productPropertyRepository = productPropertyRepository;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.checkRequiredFields();
		this.saveProductProperty();
		this.findAllProductProperties();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("productPropertyRegistered", this.productPropertyParam);
		return super.returnBusinessData();
	}

	private void checkRequiredFields() throws RequiredFieldNotFoundException {
		List<String> errorList = new ArrayList<>();

		if (StringUtils.isBlank(this.productPropertyParam.getName()))
			errorList.add("Please, enter name.");
		if (this.productPropertyParam.getUserIdentity() == null)
			errorList.add("Please, the product property need to be associated with a user.");

		if (!errorList.isEmpty())
			throw new RequiredFieldNotFoundException("Required Field Not Found", errorList);
	}

	private void saveProductProperty() {
		this.productPropertyRepository.save(this.productPropertyParam);
	}

	private void findAllProductProperties() {
		this.setArtifact("productPropertyList", this.productPropertyRepository.findByUserIdentityOrderByNameAsc(productPropertyParam.getUserIdentity()));
	}
}