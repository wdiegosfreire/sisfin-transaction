package br.com.dfdevforge.sisfintransaction.supermarket.model.productproperty.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.DataForEditionNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.productproperty.entities.ProductPropertyEntity;
import br.com.dfdevforge.sisfintransaction.supermarket.model.productproperty.repositories.ProductPropertyRepository;

@Service
@RequestScope
public class ProductPropertyAccessEditionService extends ProductPropertyBaseService implements CommonService {
	private final ProductPropertyRepository productPropertyRepository;

	@Autowired
	public ProductPropertyAccessEditionService(ProductPropertyRepository productPropertyRepository) {
		this.productPropertyRepository = productPropertyRepository;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findById();
	}

	private void findById() throws DataForEditionNotFoundException {
		ProductPropertyEntity productProperty = this.productPropertyRepository.findByIdentity(this.productPropertyParam.getIdentity());

		if (productProperty == null)
			throw new DataForEditionNotFoundException();

		this.setArtifact("productProperty", productProperty);
	}
}