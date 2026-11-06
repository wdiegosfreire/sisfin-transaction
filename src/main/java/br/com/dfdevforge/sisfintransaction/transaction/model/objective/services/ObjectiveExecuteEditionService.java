package br.com.dfdevforge.sisfintransaction.transaction.model.objective.services;

import java.io.IOException;
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

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.DataForEditionNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.TaxCouponFileInvalidException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.commons.utils.Utils;
import br.com.dfdevforge.sisfintransaction.supermarket.model.address.entities.AddressEntity;
import br.com.dfdevforge.sisfintransaction.supermarket.model.company.entities.CompanyEntity;
import br.com.dfdevforge.sisfintransaction.supermarket.model.company.services.CompanyExecuteRegistrationService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.productcompany.entities.ProductCompanyEntity;
import br.com.dfdevforge.sisfintransaction.supermarket.model.productcompany.services.ProductCompanyExecuteRegistrationService;
import br.com.dfdevforge.sisfintransaction.transaction.model.objective.entities.ObjectiveEntity;
import br.com.dfdevforge.sisfintransaction.transaction.model.objective.repositories.ObjectiveRepository;
import br.com.dfdevforge.sisfintransaction.transaction.model.objectiveitem.entities.ObjectiveItemEntity;
import br.com.dfdevforge.sisfintransaction.transaction.model.objectiveitem.repositories.ObjectiveItemRepository;
import br.com.dfdevforge.sisfintransaction.transaction.model.objectivemovement.entities.ObjectiveMovementEntity;
import br.com.dfdevforge.sisfintransaction.transaction.model.objectivemovement.repositories.ObjectiveMovementRepository;

@Service
@RequestScope
public class ObjectiveExecuteEditionService extends ObjectiveBaseService implements CommonService {
	private final ObjectiveRepository objectiveRepository;
	private final ObjectiveMovementRepository objectiveMovementRepository;
	private final ObjectiveItemRepository objectiveItemRepository;
	private final CompanyExecuteRegistrationService companyExecuteRegistrationService;
	private final ProductCompanyExecuteRegistrationService productCompanyExecuteRegistrationService;
	private final ObjectMapper objectMapper;

	@Autowired
	public ObjectiveExecuteEditionService(ObjectiveRepository objectiveRepository, ObjectiveMovementRepository objectiveMovementRepository, ObjectiveItemRepository objectiveItemRepository, CompanyExecuteRegistrationService companyExecuteRegistrationService, ProductCompanyExecuteRegistrationService productCompanyExecuteRegistrationService, ObjectMapper objectMapper) {
		this.objectiveRepository = objectiveRepository;
		this.objectiveMovementRepository = objectiveMovementRepository;
		this.objectiveItemRepository = objectiveItemRepository;
		this.companyExecuteRegistrationService = companyExecuteRegistrationService;
		this.productCompanyExecuteRegistrationService = productCompanyExecuteRegistrationService;
		this.objectMapper = objectMapper;
	}

	private CompanyEntity company;
	private JsonNode taxCouponJson;
	private List<ProductCompanyEntity> productCompanyList;

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findByIdentity();
		this.saveObjectiveEdition();
		this.saveObjectiveMovementsEdition();
		this.saveObjectiveItemsEdition();

		if (this.objectiveParam.getProps() != null && this.objectiveParam.getProps().getTaxCouponFile() != null) {
			this.readTaxCouponFile();
			this.buildCompanyFromTaxCoupon();
			this.saveCompany();
			this.buildProductCompanyFromTaxCoupon();
			this.saveProductCompany();
		}
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("objectiveRegistered", this.objectiveParam);
		return super.returnBusinessData();
	}

	private void findByIdentity() throws DataForEditionNotFoundException {
		ObjectiveEntity objective = this.objectiveRepository.findByIdentity(this.objectiveParam.getIdentity());

		if (objective == null)
			throw new DataForEditionNotFoundException();
	}

	private void saveObjectiveEdition() {
		this.objectiveRepository.save(this.objectiveParam);
	}

	private void saveObjectiveMovementsEdition() {
		this.objectiveMovementRepository.deleteAll(this.objectiveMovementRepository.findByObjective(objectiveParam));
		this.objectiveMovementRepository.flush();

		Date now = new Date();
		for (ObjectiveMovementEntity objectiveMovementEdited : this.objectiveParam.getObjectiveMovementList()) {
			objectiveMovementEdited.setIdentity(null);
			objectiveMovementEdited.setRegistrationDate(now);
			objectiveMovementEdited.setObjective(this.objectiveParam);
			objectiveMovementEdited.setUserIdentity(this.objectiveParam.getUserIdentity());
			this.objectiveMovementRepository.save(objectiveMovementEdited);
		}
	}

	private void saveObjectiveItemsEdition() {
		this.objectiveItemRepository.deleteAll(this.objectiveItemRepository.findByObjective(objectiveParam));
		this.objectiveItemRepository.flush();
		
		for (ObjectiveItemEntity objectiveItemEdited : this.objectiveParam.getObjectiveItemList()) {
			objectiveItemEdited.setIdentity(null);
			objectiveItemEdited.setObjective(this.objectiveParam);
			objectiveItemEdited.setUserIdentity(this.objectiveParam.getUserIdentity());
			this.objectiveItemRepository.save(objectiveItemEdited);
		}
	}

	private void readTaxCouponFile() throws TaxCouponFileInvalidException {
		String taxCouponFile = this.objectiveParam.getProps().getTaxCouponFile();
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
		company.setTaxNumber(Utils.value.formatToCnpj(taxNumber));
		company.setName(this.readText(this.taxCouponJson, "companyName"));
		company.setFantasyName(fantasyName != null ? fantasyName : company.getName());
		company.setIsInactive(false);
		company.setAddress(this.buildAddressFromTaxCoupon(this.taxCouponJson.path("address")));
		company.setUserIdentity(this.objectiveParam.getUserIdentity());
	}

	private void saveCompany() throws BaseException {
		this.companyExecuteRegistrationService.setParams(this.company);
		Map<String, Object> result = this.companyExecuteRegistrationService.execute();
		this.company = (CompanyEntity) result.get("companyRegistered");
	}

	private void buildProductCompanyFromTaxCoupon() {
		this.productCompanyList = new ArrayList<>();

		JsonNode itemsNode = this.taxCouponJson.path("items");

		if (!itemsNode.isArray())
			return;

		for (JsonNode itemNode : itemsNode) {
			String code = this.readText(itemNode, "code");
			String description = this.readText(itemNode, "description");

			if (code == null || description == null)
				continue;

			ProductCompanyEntity productCompany = new ProductCompanyEntity();
			productCompany.setCode(code);
			productCompany.setDescription(description);
			productCompany.setCompany(this.company);
			productCompany.setUserIdentity(this.objectiveParam.getUserIdentity());

			this.productCompanyList.add(productCompany);
		}
	}

	private void saveProductCompany() throws BaseException {
		for (ProductCompanyEntity productCompany : this.productCompanyList) {
			this.productCompanyExecuteRegistrationService.setParams(productCompany);
			this.productCompanyExecuteRegistrationService.execute();
		}
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
		address.setUserIdentity(this.objectiveParam.getUserIdentity());

		return address;
	}

	private String readText(JsonNode node, String fieldName) {
		String value = StringUtils.trimToNull(node.path(fieldName).asText(null));
		return value == null ? null : value.replaceAll("\\s{2,}", " ");
	}
}