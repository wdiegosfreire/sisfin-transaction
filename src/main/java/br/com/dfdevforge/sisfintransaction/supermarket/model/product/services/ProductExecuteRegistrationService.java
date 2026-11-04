package br.com.dfdevforge.sisfintransaction.supermarket.model.product.services;

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
import br.com.dfdevforge.sisfintransaction.supermarket.model.product.repositories.ProductRepository;

@Service
@RequestScope
public class ProductExecuteRegistrationService extends ProductBaseService implements CommonService {
	private final ProductRepository productRepository;

	@Autowired
	public ProductExecuteRegistrationService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.checkRequiredFields();
		this.saveProduct();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("productRegistered", this.productParam);
		return super.returnBusinessData();
	}

	private void checkRequiredFields() throws RequiredFieldNotFoundException {
		List<String> errorList = new ArrayList<>();

		if (StringUtils.isBlank(this.productParam.getBarcode()))
			errorList.add("Please, barcode is required.");
		if (this.productParam.getItemPerBarcode() == null)
			errorList.add("Please, item per barcode is required.");
		if (this.productParam.getQuantityPerItem() == null)
			errorList.add("Please, quantity per item is required.");
		if (this.productParam.getIsFavorite() == null)
			errorList.add("Please, is favorite is required.");
		if (this.productParam.getBrand() == null)
			errorList.add("Please, brand is required.");
		if (this.productParam.getProductCategory() == null)
			errorList.add("Please, product category is required.");
		if (this.productParam.getMeasurementUnit() == null)
			errorList.add("Please, measurement unit is required.");
		if (this.productParam.getUserIdentity() == null)
			errorList.add("Please, the product need to be associated with a user.");

		if (!errorList.isEmpty())
			throw new RequiredFieldNotFoundException("Required Field Not Found", errorList);
	}

	private void saveProduct() {
		this.productRepository.save(this.productParam);
	}
}