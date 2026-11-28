package br.com.dfdevforge.sisfintransaction.supermarket.model.productinfo.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.DataForExclusionNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.productinfo.entities.ProductInfoEntity;
import br.com.dfdevforge.sisfintransaction.supermarket.model.productinfo.repositories.ProductInfoRepository;

@Service
@RequestScope
public class ProductInfoExecuteExclusionService extends ProductInfoBaseService implements CommonService {
	private final ProductInfoRepository productInfoRepository;

	@Autowired
	public ProductInfoExecuteExclusionService(ProductInfoRepository productInfoRepository) {
		this.productInfoRepository = productInfoRepository;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findById();
		this.deleteProductInfo();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("productInfoRegistered", this.productInfoParam);
		return super.returnBusinessData();
	}

	private void findById() throws DataForExclusionNotFoundException {
		ProductInfoEntity productInfo = this.productInfoRepository.findByIdentity(this.productInfoParam.getIdentity());

		if (productInfo == null)
			throw new DataForExclusionNotFoundException();
	}

	private void deleteProductInfo() {
		this.productInfoRepository.delete(this.productInfoParam);
	}
}