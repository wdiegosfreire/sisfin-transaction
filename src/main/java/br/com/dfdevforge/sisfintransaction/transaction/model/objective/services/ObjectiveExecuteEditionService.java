package br.com.dfdevforge.sisfintransaction.transaction.model.objective.services;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.DataForEditionNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.TaxCouponFileInvalidException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.commons.utils.Utils;
import br.com.dfdevforge.sisfintransaction.supermarket.model.address.entities.AddressEntity;
import br.com.dfdevforge.sisfintransaction.supermarket.model.address.repositories.AddressRepository;
import br.com.dfdevforge.sisfintransaction.supermarket.model.company.entities.CompanyEntity;
import br.com.dfdevforge.sisfintransaction.supermarket.model.company.repositories.CompanyRepository;
import br.com.dfdevforge.sisfintransaction.transaction.model.objective.entities.ObjectiveEntity;
import br.com.dfdevforge.sisfintransaction.transaction.model.objective.repositories.ObjectiveRepository;
import br.com.dfdevforge.sisfintransaction.transaction.model.objectiveitem.entities.ObjectiveItemEntity;
import br.com.dfdevforge.sisfintransaction.transaction.model.objectiveitem.repositories.ObjectiveItemRepository;
import br.com.dfdevforge.sisfintransaction.transaction.model.objectivemovement.entities.ObjectiveMovementEntity;
import br.com.dfdevforge.sisfintransaction.transaction.model.objectivemovement.repositories.ObjectiveMovementRepository;

@Service
@RequestScope
@Transactional(rollbackFor = java.lang.Exception.class)
public class ObjectiveExecuteEditionService extends ObjectiveBaseService implements CommonService {
	private final ObjectiveRepository objectiveRepository;
	private final ObjectiveMovementRepository objectiveMovementRepository;
	private final ObjectiveItemRepository objectiveItemRepository;
	private final CompanyRepository companyRepository;
	private final AddressRepository addressRepository;
	private final ObjectMapper objectMapper;

	@Autowired
	public ObjectiveExecuteEditionService(ObjectiveRepository objectiveRepository, ObjectiveMovementRepository objectiveMovementRepository, ObjectiveItemRepository objectiveItemRepository, CompanyRepository companyRepository, AddressRepository addressRepository, ObjectMapper objectMapper) {
		this.objectiveRepository = objectiveRepository;
		this.objectiveMovementRepository = objectiveMovementRepository;
		this.objectiveItemRepository = objectiveItemRepository;
		this.companyRepository = companyRepository;
		this.addressRepository = addressRepository;
		this.objectMapper = objectMapper;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findByIdentity();
		this.saveObjectiveEdition();
		this.saveObjectiveMovementsEdition();
		this.saveObjectiveItemsEdition();

		if (this.objectiveParam.getProps() != null && this.objectiveParam.getProps().getTaxCouponFile() != null)
			this.processTaxCouponFile();
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

		Date now = new Date();
		for (ObjectiveMovementEntity objectiveMovementEdited : this.objectiveParam.getObjectiveMovementList()) {
			objectiveMovementEdited.setIdentity(Math.abs(objectiveMovementEdited.getIdentity()));

			objectiveMovementEdited.setRegistrationDate(now);
			objectiveMovementEdited.setObjective(this.objectiveParam);
			objectiveMovementEdited.setUserIdentity(this.objectiveParam.getUserIdentity());
			this.objectiveMovementRepository.save(objectiveMovementEdited);
		}
	}

	private void saveObjectiveItemsEdition() {
		this.objectiveItemRepository.deleteAll(this.objectiveItemRepository.findByObjective(objectiveParam));
		
		for (ObjectiveItemEntity objectiveItemEdited : this.objectiveParam.getObjectiveItemList()) {
			objectiveItemEdited.setObjective(this.objectiveParam);
			objectiveItemEdited.setUserIdentity(this.objectiveParam.getUserIdentity());
			this.objectiveItemRepository.save(objectiveItemEdited);
		}
	}

	private void processTaxCouponFile() throws TaxCouponFileInvalidException {
		JsonNode taxCoupon = this.readTaxCouponFile();

		this.saveCompanyFromTaxCoupon(taxCoupon);
	}

	/*
	 * O arquivo chega do frontend como data URL ("data:application/json;base64,<conteudo>").
	 */
	private JsonNode readTaxCouponFile() throws TaxCouponFileInvalidException {
		String taxCouponFile = this.objectiveParam.getProps().getTaxCouponFile();
		String base64Content = taxCouponFile.contains("base64,") ? taxCouponFile.split("base64,")[1] : taxCouponFile;

		try {
			return this.objectMapper.readTree(Utils.decrypt.fromBase64(base64Content));
		}
		catch (IllegalArgumentException | IOException e) {
			Utils.log.stackTrace(e);
			throw new TaxCouponFileInvalidException();
		}
	}

	/*
	 * Se a empresa (CNPJ) ja estiver cadastrada para o usuario, ela e reaproveitada para evitar duplicidade.
	 */
	private CompanyEntity saveCompanyFromTaxCoupon(JsonNode taxCoupon) throws TaxCouponFileInvalidException {
		String taxNumber = this.readText(taxCoupon, "taxIdNumber");

		if (taxNumber == null)
			throw new TaxCouponFileInvalidException();

		taxNumber = Utils.value.formatToCnpj(taxNumber);

		CompanyEntity company = this.companyRepository.findFirstByTaxNumberAndUserIdentity(taxNumber, this.objectiveParam.getUserIdentity());

		if (company != null)
			return company;

		String fantasyName = this.readText(taxCoupon, "fantasyName");

		company = new CompanyEntity();
		company.setTaxNumber(taxNumber);
		company.setName(this.readText(taxCoupon, "companyName"));
		company.setFantasyName(fantasyName != null ? fantasyName : company.getName());
		company.setIsInactive(false);
		company.setAddress(this.saveAddressFromTaxCoupon(taxCoupon.path("address")));
		company.setUserIdentity(this.objectiveParam.getUserIdentity());

		return this.companyRepository.save(company);
	}

	private AddressEntity saveAddressFromTaxCoupon(JsonNode addressNode) {
		if (addressNode.isMissingNode() || addressNode.isNull())
			return null;

		AddressEntity address = new AddressEntity();
		address.setStreet(this.readText(addressNode, "street"));
		address.setNumber(this.readText(addressNode, "number"));
		address.setNeighborhood(this.readText(addressNode, "neighborhood"));
		address.setZipCode(Utils.value.formatToZipCode(this.readText(addressNode, "zipCode")));
		address.setCity(this.readText(addressNode.path("city"), "name"));
		address.setUserIdentity(this.objectiveParam.getUserIdentity());

		return this.addressRepository.save(address);
	}

	private String readText(JsonNode node, String fieldName) {
		String value = StringUtils.trimToNull(node.path(fieldName).asText(null));
		return value == null ? null : value.replaceAll("\\s{2,}", " ");
	}
}