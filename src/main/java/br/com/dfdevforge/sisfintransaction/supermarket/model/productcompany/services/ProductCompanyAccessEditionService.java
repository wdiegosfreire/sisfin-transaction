package br.com.dfdevforge.sisfintransaction.supermarket.model.productcompany.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.DataForEditionNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.productcompany.entities.ProductCompanyEntity;
import br.com.dfdevforge.sisfintransaction.supermarket.model.productcompany.repositories.ProductCompanyRepository;

@Service
@RequestScope
public class ProductCompanyAccessEditionService extends ProductCompanyBaseService implements CommonService {
	private final ProductCompanyRepository productCompanyRepository;

	@Autowired
	public ProductCompanyAccessEditionService(ProductCompanyRepository productCompanyRepository) {
		this.productCompanyRepository = productCompanyRepository;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findByIdentity();
		this.findProducts();
	}

	private void findByIdentity() throws DataForEditionNotFoundException {
		ProductCompanyEntity productCompany = this.productCompanyRepository.findByIdentity(this.productCompanyParam.getIdentity());

		if (productCompany == null)
			throw new DataForEditionNotFoundException();

		this.setArtifact("productCompany", productCompany);
	}

	private void findProducts() {
		this.setArtifact("productListTable", this.findProductsByUserIdentityOrderByNameAsc(this.productCompanyParam.getUserIdentity()));
	}
}