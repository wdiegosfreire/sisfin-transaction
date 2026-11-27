package br.com.dfdevforge.sisfintransaction.supermarket.model.productproperty.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.productproperty.repositories.ProductPropertyRepository;

@Service
@RequestScope
public class ProductPropertyAccessModuleService extends ProductPropertyBaseService implements CommonService {
	private final ProductPropertyRepository productPropertyRepository;

	@Autowired
	public ProductPropertyAccessModuleService(ProductPropertyRepository productPropertyRepository) {
		this.productPropertyRepository = productPropertyRepository;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findAllProductProperties();
	}

	private void findAllProductProperties() {
		this.setArtifact("productPropertyList", this.productPropertyRepository.findByUserIdentityOrderByNameAsc(this.productPropertyParam.getUserIdentity()));
	}
}