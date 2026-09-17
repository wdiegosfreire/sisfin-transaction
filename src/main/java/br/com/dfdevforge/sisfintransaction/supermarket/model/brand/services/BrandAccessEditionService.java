package br.com.dfdevforge.sisfintransaction.supermarket.model.brand.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.DataForEditionNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.brand.entities.BrandEntity;
import br.com.dfdevforge.sisfintransaction.supermarket.model.brand.repositories.BrandRepository;

@Service
@RequestScope
@Transactional(rollbackFor = java.lang.Exception.class)
public class BrandAccessEditionService extends BrandBaseService implements CommonService {
	private final BrandRepository brandRepository;

	@Autowired
	public BrandAccessEditionService(BrandRepository brandRepository) {
		this.brandRepository = brandRepository;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findById();
	}

	private void findById() throws DataForEditionNotFoundException {
		BrandEntity brand = this.brandRepository.findByIdentity(this.brandParam.getIdentity());

		if (brand == null)
			throw new DataForEditionNotFoundException();

		this.setArtifact("brand", brand);
	}
}