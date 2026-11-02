package br.com.dfdevforge.sisfintransaction.supermarket.model.productcategory.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.DataForEditionNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.productcategory.entities.ProductCategoryEntity;
import br.com.dfdevforge.sisfintransaction.supermarket.model.productcategory.repositories.ProductCategoryRepository;

@Service
@RequestScope
public class ProductCategoryExecuteEditionService extends ProductCategoryBaseService implements CommonService {
	private final ProductCategoryRepository productCategoryRepository;

	@Autowired
	public ProductCategoryExecuteEditionService(ProductCategoryRepository productCategoryRepository) {
		this.productCategoryRepository = productCategoryRepository;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findById();
		this.editProductCategory();
		this.findAllProductCategories();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("productCategoryRegistered", this.productCategoryParam);
		return super.returnBusinessData();
	}

	private void findById() throws DataForEditionNotFoundException {
		ProductCategoryEntity productCategory = this.productCategoryRepository.findByIdentity(this.productCategoryParam.getIdentity());

		if (productCategory == null)
			throw new DataForEditionNotFoundException();
	}

	private void editProductCategory() {
		this.productCategoryRepository.save(this.productCategoryParam);
	}

	private void findAllProductCategories() {
		this.setArtifact("productCategoryList", this.productCategoryRepository.findByUserIdentityOrderByNameAsc(productCategoryParam.getUserIdentity()));
	}
}