package br.com.dfdevforge.sisfintransaction.supermarket.model.productcategory.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.productcategory.repositories.ProductCategoryRepository;

@Service
@RequestScope
public class ProductCategoryAccessModuleService extends ProductCategoryBaseService implements CommonService {
	private final ProductCategoryRepository productCategoryRepository;

	@Autowired
	public ProductCategoryAccessModuleService(ProductCategoryRepository productCategoryRepository) {
		this.productCategoryRepository = productCategoryRepository;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findAllProductCategories();
	}

	private void findAllProductCategories() {
		this.setArtifact("productCategoryList", this.productCategoryRepository.findByUserIdentityOrderByNameAsc(this.productCategoryParam.getUserIdentity()));
	}
}