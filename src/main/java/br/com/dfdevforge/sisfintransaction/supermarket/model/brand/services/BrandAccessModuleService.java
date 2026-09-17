package br.com.dfdevforge.sisfintransaction.supermarket.model.brand.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.brand.repositories.BrandRepository;

@Service
@RequestScope
@Transactional(rollbackFor = java.lang.Exception.class)
public class BrandAccessModuleService extends BrandBaseService implements CommonService {
	private final BrandRepository brandRepository;

	@Autowired
	public BrandAccessModuleService(BrandRepository brandRepository) {
		this.brandRepository = brandRepository;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findAllBrands();
	}

	private void findAllBrands() {
		this.setArtifact("brandList", this.brandRepository.findByUserIdentityOrderByNameAsc(this.brandParam.getUserIdentity()));
	}
}