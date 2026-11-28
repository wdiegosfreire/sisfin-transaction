package br.com.dfdevforge.sisfintransaction.supermarket.model.productcategory.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.DataForExclusionNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.productcategory.entities.ProductCategoryEntity;
import br.com.dfdevforge.sisfintransaction.supermarket.model.productcategory.repositories.ProductCategoryRepository;

@Service
@RequestScope
public class ProductCategoryExecuteExclusionService extends ProductCategoryBaseService implements CommonService {
	private final ProductCategoryRepository productCategoryRepository;

	@Autowired
	public ProductCategoryExecuteExclusionService(ProductCategoryRepository productCategoryRepository) {
		this.productCategoryRepository = productCategoryRepository;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findById();
		this.deleteProductCategory();
		this.findAllProductCategories();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("productCategoryRegistered", this.productCategoryParam);
		return super.returnBusinessData();
	}

	private void findById() throws DataForExclusionNotFoundException {
		ProductCategoryEntity productCategory = this.productCategoryRepository.findByIdentity(this.productCategoryParam.getIdentity());

		if (productCategory == null)
			throw new DataForExclusionNotFoundException();
	}

	private void deleteProductCategory() {
		this.productCategoryRepository.delete(this.productCategoryParam);
	}

	private void findAllProductCategories() {
		this.setArtifact("productCategoryList", this.productCategoryRepository.findByUserIdentityOrderByNameAsc(productCategoryParam.getUserIdentity()));
	}
}