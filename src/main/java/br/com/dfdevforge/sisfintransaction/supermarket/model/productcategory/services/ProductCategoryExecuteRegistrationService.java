package br.com.dfdevforge.sisfintransaction.supermarket.model.productcategory.services;

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
import br.com.dfdevforge.sisfintransaction.supermarket.model.productcategory.repositories.ProductCategoryRepository;

@Service
@RequestScope
public class ProductCategoryExecuteRegistrationService extends ProductCategoryBaseService implements CommonService {
	private final ProductCategoryRepository productCategoryRepository;

	@Autowired
	public ProductCategoryExecuteRegistrationService(ProductCategoryRepository productCategoryRepository) {
		this.productCategoryRepository = productCategoryRepository;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.checkRequiredFields();
		this.saveProductCategory();
		this.findAllProductCategories();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("productCategoryRegistered", this.productCategoryParam);
		return super.returnBusinessData();
	}

	private void checkRequiredFields() throws RequiredFieldNotFoundException {
		List<String> errorList = new ArrayList<>();

		if (StringUtils.isBlank(this.productCategoryParam.getName()))
			errorList.add("Please, enter name.");
		if (this.productCategoryParam.getUserIdentity() == null)
			errorList.add("Please, the product category need to be associated with a user.");

		if (!errorList.isEmpty())
			throw new RequiredFieldNotFoundException("Required Field Not Found", errorList);
	}

	private void saveProductCategory() {
		this.productCategoryRepository.save(this.productCategoryParam);
	}

	private void findAllProductCategories() {
		this.setArtifact("productCategoryList", this.productCategoryRepository.findByUserIdentityOrderByNameAsc(productCategoryParam.getUserIdentity()));
	}
}