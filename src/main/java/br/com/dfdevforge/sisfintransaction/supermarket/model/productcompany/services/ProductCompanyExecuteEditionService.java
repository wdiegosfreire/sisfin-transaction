package br.com.dfdevforge.sisfintransaction.supermarket.model.productcompany.services;

import java.util.Map;

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
public class ProductCompanyExecuteEditionService extends ProductCompanyBaseService implements CommonService {
	private final ProductCompanyRepository productCompanyRepository;

	@Autowired
	public ProductCompanyExecuteEditionService(ProductCompanyRepository productCompanyRepository) {
		this.productCompanyRepository = productCompanyRepository;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findById();
		this.editProductCompany();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("productCompanyRegistered", this.productCompanyParam);
		return super.returnBusinessData();
	}

	private void findById() throws DataForEditionNotFoundException {
		ProductCompanyEntity productCompany = this.productCompanyRepository.findByIdentity(this.productCompanyParam.getIdentity());

		if (productCompany == null)
			throw new DataForEditionNotFoundException();
	}

	private void editProductCompany() {
		this.productCompanyRepository.save(this.productCompanyParam);
	}
}