package br.com.dfdevforge.sisfintransaction.supermarket.model.productcompany.services;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.dfdevforge.sisfintransaction.commons.enums.DatePatternEnum;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.RequiredFieldNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.TaxCouponFileInvalidException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.commons.utils.Utils;
import br.com.dfdevforge.sisfintransaction.supermarket.model.address.entities.AddressEntity;
import br.com.dfdevforge.sisfintransaction.supermarket.model.company.entities.CompanyEntity;
import br.com.dfdevforge.sisfintransaction.supermarket.model.company.services.CompanyExecuteRegistrationService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.productcompany.entities.ProductCompanyEntity;
import br.com.dfdevforge.sisfintransaction.supermarket.model.productcompany.repositories.ProductCompanyRepository;
import br.com.dfdevforge.sisfintransaction.supermarket.model.productpricehistory.entities.ProductPriceHistoryEntity;
import br.com.dfdevforge.sisfintransaction.supermarket.model.productpricehistory.services.ProductPriceHistoryExecuteRegistrationService;

@Service
@RequestScope
public class ProductCompanyExecuteRegistrationService extends ProductCompanyBaseService implements CommonService {
	private final ProductCompanyRepository productCompanyRepository;
	private final CompanyExecuteRegistrationService companyExecuteRegistrationService;
	private final ProductPriceHistoryExecuteRegistrationService productPriceHistoryExecuteRegistrationService;
	private final ObjectMapper objectMapper;

	@Autowired
	public ProductCompanyExecuteRegistrationService(ProductCompanyRepository productCompanyRepository, CompanyExecuteRegistrationService companyExecuteRegistrationService, ProductPriceHistoryExecuteRegistrationService productPriceHistoryExecuteRegistrationService, ObjectMapper objectMapper) {
		this.productCompanyRepository = productCompanyRepository;
		this.companyExecuteRegistrationService = companyExecuteRegistrationService;
		this.productPriceHistoryExecuteRegistrationService = productPriceHistoryExecuteRegistrationService;
		this.objectMapper = objectMapper;
	}

	private CompanyEntity company;
	private JsonNode taxCouponJson;
	private final List<ProductCompanyEntity> productCompanyRegisteredList = new ArrayList<>();

	@Override
	public void executeBusinessRule() throws BaseException {
		this.checkTaxCouponFile();
		this.readTaxCouponFile();
		this.buildCompanyFromTaxCoupon();
		this.saveCompany();
		this.saveItemsFromTaxCoupon();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("companyRegistered", this.company);
		this.setArtifact("productCompanyRegisteredList", this.productCompanyRegisteredList);
		return super.returnBusinessData();
	}

	private void checkTaxCouponFile() throws RequiredFieldNotFoundException {
		List<String> errorList = new ArrayList<>();

		if (this.productCompanyParam.getProps() == null || StringUtils.isBlank(this.productCompanyParam.getProps().getTaxCouponFile()))
			errorList.add("Please, tax coupon file is required.");
		if (this.productCompanyParam.getUserIdentity() == null)
			errorList.add("Please, the product company need to be associated with a user.");

		if (!errorList.isEmpty())
			throw new RequiredFieldNotFoundException("Required Field Not Found", errorList);
	}

	private void readTaxCouponFile() throws TaxCouponFileInvalidException {
		String taxCouponFile = this.productCompanyParam.getProps().getTaxCouponFile();
		String base64Content = taxCouponFile.contains("base64,") ? taxCouponFile.split("base64,")[1] : taxCouponFile;

		try {
			this.taxCouponJson = this.objectMapper.readTree(Utils.decrypt.fromBase64(base64Content));
		}
		catch (IllegalArgumentException | IOException e) {
			Utils.log.stackTrace(e);
			throw new TaxCouponFileInvalidException();
		}
	}

	private void buildCompanyFromTaxCoupon() throws TaxCouponFileInvalidException {
		String taxNumber = this.readText(this.taxCouponJson, "taxIdNumber");

		if (taxNumber == null)
			throw new TaxCouponFileInvalidException();

		String fantasyName = this.readText(this.taxCouponJson, "fantasyName");

		this.company = new CompanyEntity();
		this.company.setTaxNumber(Utils.value.formatToCnpj(taxNumber));
		this.company.setName(this.readText(this.taxCouponJson, "companyName"));
		this.company.setFantasyName(fantasyName != null ? fantasyName : this.company.getName());
		this.company.setIsInactive(false);
		this.company.setAddress(this.buildAddressFromTaxCoupon(this.taxCouponJson.path("address")));
		this.company.setUserIdentity(this.productCompanyParam.getUserIdentity());
	}

	private void saveCompany() throws BaseException {
		this.companyExecuteRegistrationService.setParams(this.company);
		Map<String, Object> result = this.companyExecuteRegistrationService.execute();

		this.company = (CompanyEntity) result.get("companyRegistered");
	}

	private void saveItemsFromTaxCoupon() throws BaseException {
		JsonNode itemsNode = this.taxCouponJson.path("items");

		if (!itemsNode.isArray())
			return;

		Date purchaseDate = Utils.date.toDateFromString(this.readText(this.taxCouponJson, "emissionDate"), DatePatternEnum.PT_BR_BARS_DIA_MES_ANO_HOR_MIN_SEG);

		if (purchaseDate == null)
			throw new TaxCouponFileInvalidException();

		for (JsonNode itemNode : itemsNode) {
			ProductCompanyEntity productCompany = this.buildProductCompanyFromTaxCoupon(itemNode);

			if (productCompany == null)
				continue;

			productCompany = this.registerProductCompany(productCompany);
			this.productCompanyRegisteredList.add(productCompany);

			ProductPriceHistoryEntity productPriceHistory = this.buildProductPriceHistoryFromTaxCoupon(itemNode, productCompany, purchaseDate);

			if (productPriceHistory != null)
				this.saveProductPriceHistory(productPriceHistory);
		}
	}

	private ProductCompanyEntity registerProductCompany(ProductCompanyEntity productCompany) throws RequiredFieldNotFoundException {
		this.checkRequiredFields(productCompany);

		ProductCompanyEntity productCompanyFound = this.productCompanyRepository.findFirstByCodeAndCompanyAndUserIdentity(productCompany.getCode(), productCompany.getCompany(), productCompany.getUserIdentity());

		if (productCompanyFound != null)
			return productCompanyFound;

		this.standardizeData(productCompany);
		return this.productCompanyRepository.save(productCompany);
	}

	private void checkRequiredFields(ProductCompanyEntity productCompany) throws RequiredFieldNotFoundException {
		List<String> errorList = new ArrayList<>();

		if (StringUtils.isBlank(productCompany.getCode()))
			errorList.add("Please, code is required.");
		if (StringUtils.isBlank(productCompany.getDescription()))
			errorList.add("Please, description is required.");
		if (productCompany.getCompany() == null)
			errorList.add("Please, company is required.");
		if (productCompany.getUserIdentity() == null)
			errorList.add("Please, the product company need to be associated with a user.");

		if (!errorList.isEmpty())
			throw new RequiredFieldNotFoundException("Required Field Not Found", errorList);
	}

	private void standardizeData(ProductCompanyEntity productCompany) {
		productCompany.setDescription(productCompany.getDescription().toUpperCase());
	}

	private ProductCompanyEntity buildProductCompanyFromTaxCoupon(JsonNode itemNode) {
		String code = this.readText(itemNode, "code");
		String description = this.readText(itemNode, "description");

		if (code == null || description == null)
			return null;

		ProductCompanyEntity productCompany = new ProductCompanyEntity();
		productCompany.setCode(code);
		productCompany.setDescription(description);
		productCompany.setCompany(this.company);
		productCompany.setUserIdentity(this.productCompanyParam.getUserIdentity());

		return productCompany;
	}

	private ProductPriceHistoryEntity buildProductPriceHistoryFromTaxCoupon(JsonNode itemNode, ProductCompanyEntity productCompany, Date purchaseDate) {
		JsonNode priceNode = itemNode.path("price");

		if (!priceNode.isNumber())
			return null;

		ProductPriceHistoryEntity productPriceHistory = new ProductPriceHistoryEntity();
		productPriceHistory.setPrice(BigDecimal.valueOf(priceNode.asDouble()));
		productPriceHistory.setPurchaseDate(purchaseDate);
		productPriceHistory.setProductCompany(productCompany);
		productPriceHistory.setUserIdentity(this.productCompanyParam.getUserIdentity());

		return productPriceHistory;
	}

	private void saveProductPriceHistory(ProductPriceHistoryEntity productPriceHistory) throws BaseException {
		this.productPriceHistoryExecuteRegistrationService.setParams(productPriceHistory);
		this.productPriceHistoryExecuteRegistrationService.execute();
	}

	private AddressEntity buildAddressFromTaxCoupon(JsonNode addressNode) {
		if (addressNode.isMissingNode() || addressNode.isNull())
			return null;

		String addressNumber = this.readText(addressNode, "number");

		AddressEntity address = new AddressEntity();
		address.setStreet(this.readText(addressNode, "street"));
		address.setNumber(addressNumber != null ? addressNumber : "SN");
		address.setNeighborhood(this.readText(addressNode, "neighborhood"));
		address.setZipCode(Utils.value.formatToZipCode(this.readText(addressNode, "zipCode")));
		address.setCity(this.readText(addressNode.path("city"), "name"));
		address.setUserIdentity(this.productCompanyParam.getUserIdentity());

		return address;
	}

	private String readText(JsonNode node, String fieldName) {
		String value = StringUtils.trimToNull(node.path(fieldName).asText(null));
		return value == null ? null : value.replaceAll("\\s{2,}", " ");
	}
}
