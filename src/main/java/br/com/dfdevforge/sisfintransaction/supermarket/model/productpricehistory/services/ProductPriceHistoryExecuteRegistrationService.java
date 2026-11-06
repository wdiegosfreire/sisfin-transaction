package br.com.dfdevforge.sisfintransaction.supermarket.model.productpricehistory.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.RequiredFieldNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.productpricehistory.entities.ProductPriceHistoryEntity;
import br.com.dfdevforge.sisfintransaction.supermarket.model.productpricehistory.repositories.ProductPriceHistoryRepository;

@Service
@RequestScope
public class ProductPriceHistoryExecuteRegistrationService extends ProductPriceHistoryBaseService implements CommonService {
	private final ProductPriceHistoryRepository productPriceHistoryRepository;

	@Autowired
	public ProductPriceHistoryExecuteRegistrationService(ProductPriceHistoryRepository productPriceHistoryRepository) {
		this.productPriceHistoryRepository = productPriceHistoryRepository;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.checkRequiredFields();

		if (this.productPriceHistoryDoesntExist())
			this.saveProductPriceHistory();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("productPriceHistoryRegistered", this.productPriceHistoryParam);
		return super.returnBusinessData();
	}

	private void checkRequiredFields() throws RequiredFieldNotFoundException {
		List<String> errorList = new ArrayList<>();

		if (this.productPriceHistoryParam.getPrice() == null)
			errorList.add("Please, price is required.");
		if (this.productPriceHistoryParam.getPurchaseDate() == null)
			errorList.add("Please, purchase date is required.");
		if (this.productPriceHistoryParam.getProductCompany() == null)
			errorList.add("Please, product company is required.");
		if (this.productPriceHistoryParam.getUserIdentity() == null)
			errorList.add("Please, the product price history need to be associated with a user.");

		if (!errorList.isEmpty())
			throw new RequiredFieldNotFoundException("Required Field Not Found", errorList);
	}

	/*
	 * Se ja existir um preco registrado para o produto na mesma data/hora de compra, ele e reaproveitado para evitar duplicidade.
	 */
	private boolean productPriceHistoryDoesntExist() {
		ProductPriceHistoryEntity productPriceHistory = this.productPriceHistoryRepository.findFirstByProductCompanyAndPurchaseDateAndUserIdentity(this.productPriceHistoryParam.getProductCompany(), this.productPriceHistoryParam.getPurchaseDate(), this.productPriceHistoryParam.getUserIdentity());

		if (productPriceHistory == null)
			return true;

		this.productPriceHistoryParam = productPriceHistory;
		return false;
	}

	private void saveProductPriceHistory() {
		this.productPriceHistoryRepository.save(this.productPriceHistoryParam);
	}
}