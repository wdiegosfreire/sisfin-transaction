package br.com.dfdevforge.sisfintransaction.supermarket.model.product.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.DataForExclusionNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.product.entities.ProductEntity;
import br.com.dfdevforge.sisfintransaction.supermarket.model.product.repositories.ProductRepository;

@Service
@RequestScope
public class ProductExecuteExclusionService extends ProductBaseService implements CommonService {
	private final ProductRepository productRepository;

	@Autowired
	public ProductExecuteExclusionService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findById();
		this.deleteProduct();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("productRegistered", this.productParam);
		return super.returnBusinessData();
	}

	private void findById() throws DataForExclusionNotFoundException {
		ProductEntity product = this.productRepository.findByIdentity(this.productParam.getIdentity());

		if (product == null)
			throw new DataForExclusionNotFoundException();
	}

	private void deleteProduct() {
		this.productRepository.delete(this.productParam);
	}
}