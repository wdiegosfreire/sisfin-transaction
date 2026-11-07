package br.com.dfdevforge.sisfintransaction.supermarket.model.product.services;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;

@Service
@RequestScope
public class ProductAccessRegistrationService extends ProductBaseService implements CommonService {
	@Override
	public void executeBusinessRule() throws BaseException {
		this.findBrands();
		this.findMeasurementUnits();
		this.findProductCategories();
	}

	private void findBrands() {
		this.setArtifact("brandListCombo", this.findBrandsByUserIdentityOrderByNameAsc(this.productParam.getUserIdentity()));
	}

	private void findMeasurementUnits() {
		this.setArtifact("measurementUnitListCombo", this.findMeasurementUnitsByUserIdentityOrderByNameAsc(this.productParam.getUserIdentity()));
	}

	private void findProductCategories() {
		this.setArtifact("productCategoryListCombo", this.findProductCategoriesByUserIdentityOrderByNameAsc(this.productParam.getUserIdentity()));
	}
}