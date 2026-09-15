package br.com.dfdevforge.sisfintransaction.supermarket.model.brand.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.brand.repositories.BrandRepository;
import br.com.dfdevforge.sisfintransaction.supermarket.model.brand.repositories.BrandRepositoryCustomized;

@Service
@RequestScope
@Transactional(rollbackFor = java.lang.Exception.class)
public class BrandExecuteSearchService extends BrandBaseService implements CommonService {
	private final BrandRepository brandRepository;
	private final BrandRepositoryCustomized brandRepositoryCustomized;

	@Autowired
	public BrandExecuteSearchService(BrandRepository brandRepository, BrandRepositoryCustomized brandRepositoryCustomized) {
		this.brandRepository = brandRepository;
		this.brandRepositoryCustomized = brandRepositoryCustomized;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findAllBrands();
	}

	private void findAllBrands() {
		if (this.brandParam.getFilter() == null || this.brandParam.getFilter().contentEquals(""))
			this.setArtifact("brandList", this.brandRepository.findByUserIdentityOrderByNameAsc(brandParam.getUserIdentity()));
		else
			this.setArtifact("brandList", this.brandRepositoryCustomized.searchInAllProperties(this.brandParam));
	}
}