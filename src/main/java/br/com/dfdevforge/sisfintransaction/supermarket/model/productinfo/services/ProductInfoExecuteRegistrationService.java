package br.com.dfdevforge.sisfintransaction.supermarket.model.productinfo.services;

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
import br.com.dfdevforge.sisfintransaction.supermarket.model.productinfo.repositories.ProductInfoRepository;

@Service
@RequestScope
public class ProductInfoExecuteRegistrationService extends ProductInfoBaseService implements CommonService {
	private final ProductInfoRepository productInfoRepository;

	@Autowired
	public ProductInfoExecuteRegistrationService(ProductInfoRepository productInfoRepository) {
		this.productInfoRepository = productInfoRepository;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.checkRequiredFields();
		this.saveProductInfo();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("productInfoRegistered", this.productInfoParam);
		return super.returnBusinessData();
	}

	private void checkRequiredFields() throws RequiredFieldNotFoundException {
		List<String> errorList = new ArrayList<>();

		if (this.productInfoParam.getOrder() == null)
			errorList.add("Please, order is required.");
		if (StringUtils.isBlank(this.productInfoParam.getDescription()))
			errorList.add("Please, description is required.");
		if (this.productInfoParam.getProduct() == null)
			errorList.add("Please, product is required.");
		if (this.productInfoParam.getProductProperty() == null)
			errorList.add("Please, product property is required.");
		if (this.productInfoParam.getUserIdentity() == null)
			errorList.add("Please, the product info need to be associated with a user.");

		if (!errorList.isEmpty())
			throw new RequiredFieldNotFoundException("Required Field Not Found", errorList);
	}

	private void saveProductInfo() {
		this.productInfoRepository.save(this.productInfoParam);
	}
}