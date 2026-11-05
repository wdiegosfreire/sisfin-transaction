package br.com.dfdevforge.sisfintransaction.supermarket.model.productinfo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.DataForEditionNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.productinfo.entities.ProductInfoEntity;
import br.com.dfdevforge.sisfintransaction.supermarket.model.productinfo.repositories.ProductInfoRepository;

@Service
@RequestScope
public class ProductInfoAccessEditionService extends ProductInfoBaseService implements CommonService {
	private final ProductInfoRepository productInfoRepository;

	@Autowired
	public ProductInfoAccessEditionService(ProductInfoRepository productInfoRepository) {
		this.productInfoRepository = productInfoRepository;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findById();
	}

	private void findById() throws DataForEditionNotFoundException {
		ProductInfoEntity productInfo = this.productInfoRepository.findByIdentity(this.productInfoParam.getIdentity());

		if (productInfo == null)
			throw new DataForEditionNotFoundException();

		this.setArtifact("productInfo", productInfo);
	}
}