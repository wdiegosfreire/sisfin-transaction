package br.com.dfdevforge.sisfintransaction.supermarket.model.product.services;

import java.util.Map;

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
public class ProductExecuteEditionService extends ProductBaseService implements CommonService {
	private final ProductRepository productRepository;

	@Autowired
	public ProductExecuteEditionService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findById();
		this.editProduct();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("productRegistered", this.productParam);
		return super.returnBusinessData();
	}

	private void findById() throws DataForEditionNotFoundException {
		ProductEntity product = this.productRepository.findByIdentity(this.productParam.getIdentity());

		if (product == null)
			throw new DataForEditionNotFoundException();
	}

	private void editProduct() {
		this.productRepository.save(this.productParam);
	}
}