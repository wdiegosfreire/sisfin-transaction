package br.com.dfdevforge.sisfintransaction.supermarket.model.productproperty.services;

import java.util.Map;

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
public class ProductPropertyExecuteEditionService extends ProductPropertyBaseService implements CommonService {
	private final ProductPropertyRepository productPropertyRepository;

	@Autowired
	public ProductPropertyExecuteEditionService(ProductPropertyRepository productPropertyRepository) {
		this.productPropertyRepository = productPropertyRepository;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findById();
		this.editProductProperty();
		this.findAllProductProperties();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("productPropertyRegistered", this.productPropertyParam);
		return super.returnBusinessData();
	}

	private void findById() throws DataForEditionNotFoundException {
		ProductPropertyEntity productProperty = this.productPropertyRepository.findByIdentity(this.productPropertyParam.getIdentity());

		if (productProperty == null)
			throw new DataForEditionNotFoundException();
	}

	private void editProductProperty() {
		this.productPropertyRepository.save(this.productPropertyParam);
	}

	private void findAllProductProperties() {
		this.setArtifact("productPropertyList", this.productPropertyRepository.findByUserIdentityOrderByNameAsc(productPropertyParam.getUserIdentity()));
	}
}