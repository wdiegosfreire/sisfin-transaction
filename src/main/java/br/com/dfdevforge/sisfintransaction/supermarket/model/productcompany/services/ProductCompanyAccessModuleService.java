package br.com.dfdevforge.sisfintransaction.supermarket.model.productcompany.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.productcompany.repositories.ProductCompanyRepository;

@Service
@RequestScope
public class ProductCompanyAccessModuleService extends ProductCompanyBaseService implements CommonService {
	private final ProductCompanyRepository productCompanyRepository;

	@Autowired
	public ProductCompanyAccessModuleService(ProductCompanyRepository productCompanyRepository) {
		this.productCompanyRepository = productCompanyRepository;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findAllProductCompanies();
	}

	private void findAllProductCompanies() {
		this.setArtifact("productCompanyList", this.productCompanyRepository.findByUserIdentityOrderByDescriptionAsc(this.productCompanyParam.getUserIdentity()));
	}
}