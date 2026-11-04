package br.com.dfdevforge.sisfintransaction.supermarket.model.product.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.DataForEditionNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.product.entities.ProductEntity;
import br.com.dfdevforge.sisfintransaction.supermarket.model.product.repositories.ProductRepository;

@Service
@RequestScope
public class ProductAccessEditionService extends ProductBaseService implements CommonService {
	private final ProductRepository productRepository;

	@Autowired
	public ProductAccessEditionService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findById();
	}

	private void findById() throws DataForEditionNotFoundException {
		ProductEntity product = this.productRepository.findByIdentity(this.productParam.getIdentity());

		if (product == null)
			throw new DataForEditionNotFoundException();

		this.setArtifact("product", product);
	}
}