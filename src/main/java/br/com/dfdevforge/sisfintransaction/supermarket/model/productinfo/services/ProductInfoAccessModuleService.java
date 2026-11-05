package br.com.dfdevforge.sisfintransaction.supermarket.model.productinfo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.productinfo.repositories.ProductInfoRepository;

@Service
@RequestScope
public class ProductInfoAccessModuleService extends ProductInfoBaseService implements CommonService {
	private final ProductInfoRepository productInfoRepository;

	@Autowired
	public ProductInfoAccessModuleService(ProductInfoRepository productInfoRepository) {
		this.productInfoRepository = productInfoRepository;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findAllProductInfos();
	}

	private void findAllProductInfos() {
		this.setArtifact("productInfoList", this.productInfoRepository.findByUserIdentityOrderByOrderAsc(this.productInfoParam.getUserIdentity()));
	}
}