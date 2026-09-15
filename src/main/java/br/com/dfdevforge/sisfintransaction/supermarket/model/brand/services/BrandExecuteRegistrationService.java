package br.com.dfdevforge.sisfintransaction.supermarket.model.brand.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.RequiredFieldNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.brand.repositories.BrandRepository;

@Service
@RequestScope
@Transactional(rollbackFor = java.lang.Exception.class)
public class BrandExecuteRegistrationService extends BrandBaseService implements CommonService {
	private final BrandRepository brandRepository;

	@Autowired
	public BrandExecuteRegistrationService(BrandRepository brandRepository) {
		this.brandRepository = brandRepository;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.checkRequiredFields();
		this.saveBrand();
		this.findAllBrands();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("brandRegistered", this.brandParam);
		return super.returnBusinessData();
	}

	private void checkRequiredFields() throws RequiredFieldNotFoundException {
		List<String> errorList = new ArrayList<>();

		if (StringUtils.isBlank(this.brandParam.getName()))
			errorList.add("Please, enter name.");
		if (this.brandParam.getUserIdentity() == null)
			errorList.add("Please, the brand need to be associated with a user.");

		if (!errorList.isEmpty())
			throw new RequiredFieldNotFoundException("Required Field Not Found", errorList);
	}

	private void saveBrand() {
		this.brandRepository.save(this.brandParam);
	}

	private void findAllBrands() {
		this.setArtifact("brandList", this.brandRepository.findByUserIdentityOrderByNameAsc(brandParam.getUserIdentity()));
	}
}