package br.com.dfdevforge.sisfintransaction.supermarket.model.brand.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.DataForExclusionNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.brand.entities.BrandEntity;
import br.com.dfdevforge.sisfintransaction.supermarket.model.brand.repositories.BrandRepository;

@Service
@RequestScope
@Transactional(rollbackFor = java.lang.Exception.class)
public class BrandExecuteExclusionService extends BrandBaseService implements CommonService {
	private final BrandRepository brandRepository;

	@Autowired
	public BrandExecuteExclusionService(BrandRepository brandRepository) {
		this.brandRepository = brandRepository;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findById();
		this.deleteBrand();
		this.findAllBrands();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("brandRegistered", this.brandParam);
		return super.returnBusinessData();
	}

	private void findById() throws DataForExclusionNotFoundException {
		BrandEntity brand = this.brandRepository.findByIdentity(this.brandParam.getIdentity());

		if (brand == null)
			throw new DataForExclusionNotFoundException();
	}

	private void deleteBrand() {
		this.brandRepository.delete(this.brandParam);
	}

	private void findAllBrands() {
		this.setArtifact("brandList", this.brandRepository.findByUserIdentityOrderByNameAsc(brandParam.getUserIdentity()));
	}
}