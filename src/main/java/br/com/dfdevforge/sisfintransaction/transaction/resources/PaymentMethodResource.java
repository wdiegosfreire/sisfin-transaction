package br.com.dfdevforge.sisfintransaction.transaction.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.dfdevforge.sisfintransaction.commons.entities.ResourceDataEntity;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.transaction.model.paymentmethod.entities.PaymentMethodEntity;
import br.com.dfdevforge.sisfintransaction.transaction.model.paymentmethod.services.PaymentMethodAccessEditionService;
import br.com.dfdevforge.sisfintransaction.transaction.model.paymentmethod.services.PaymentMethodAccessModuleService;
import br.com.dfdevforge.sisfintransaction.transaction.model.paymentmethod.services.PaymentMethodExecuteEditionService;
import br.com.dfdevforge.sisfintransaction.transaction.model.paymentmethod.services.PaymentMethodExecuteExclusionService;
import br.com.dfdevforge.sisfintransaction.transaction.model.paymentmethod.services.PaymentMethodExecuteRegistrationService;
import br.com.dfdevforge.sisfintransaction.transaction.model.paymentmethod.services.PaymentMethodExecuteSearchService;

@RestController
@RequestMapping(value = "/paymentMethod")
public class PaymentMethodResource {
	private ResourceDataEntity resourceData = new ResourceDataEntity();

	@Autowired private PaymentMethodAccessModuleService paymentMethodAccessModuleService;
	@Autowired private PaymentMethodAccessEditionService paymentMethodAccessEditionService;
	@Autowired private PaymentMethodExecuteSearchService paymentMethodExecuteSearchService;
	@Autowired private PaymentMethodExecuteEditionService paymentMethodExecuteEditionService;
	@Autowired private PaymentMethodExecuteExclusionService paymentMethodExecuteExclusionService;
	@Autowired private PaymentMethodExecuteRegistrationService paymentMethodExecuteRegistrationService;


	@PostMapping(value = "/accessModule")
	public ResponseEntity<ResourceDataEntity> accessModule(@RequestBody PaymentMethodEntity paymentMethod, @RequestParam String token) throws BaseException {
		this.paymentMethodAccessModuleService.setParams(paymentMethod, token);
		this.resourceData.setMap(this.paymentMethodAccessModuleService.execute());

		return ResponseEntity.ok(this.resourceData);
	}

	@PostMapping(value = "/accessEdition")
	public ResponseEntity<ResourceDataEntity> accessEdition(@RequestBody PaymentMethodEntity paymentMethod, @RequestParam String token) throws BaseException {
		this.paymentMethodAccessEditionService.setParams(paymentMethod, token);
		this.resourceData.setMap(this.paymentMethodAccessEditionService.execute());

		return ResponseEntity.ok(this.resourceData);
	}

	@PostMapping(value = "/executeSearch")
	public ResponseEntity<ResourceDataEntity> executeSearch(@RequestBody PaymentMethodEntity paymentMethod, @RequestParam String token) throws BaseException {
		this.paymentMethodExecuteSearchService.setParams(paymentMethod, token);
		this.resourceData.setMap(this.paymentMethodExecuteSearchService.execute());

		return ResponseEntity.ok(this.resourceData);
	}

	@PostMapping(value = "/executeEdition")
	public ResponseEntity<ResourceDataEntity> executeEdition(@RequestBody PaymentMethodEntity paymentMethod, @RequestParam String token) throws BaseException {
		this.paymentMethodExecuteEditionService.setParams(paymentMethod, token);
		this.resourceData.setMap(this.paymentMethodExecuteEditionService.execute());

		return ResponseEntity.ok(this.resourceData);
	}

	@PostMapping(value = "/executeExclusion")
	public ResponseEntity<ResourceDataEntity> executeExclusion(@RequestBody PaymentMethodEntity paymentMethod, @RequestParam String token) throws BaseException {
		this.paymentMethodExecuteExclusionService.setParams(paymentMethod, token);
		this.resourceData.setMap(this.paymentMethodExecuteExclusionService.execute());

		return ResponseEntity.ok(this.resourceData);
	}

	@PostMapping(value = "/executeRegistration")
	public ResponseEntity<ResourceDataEntity> executeRegistration(@RequestBody PaymentMethodEntity paymentMethod, @RequestParam String token) throws BaseException {
		this.paymentMethodExecuteRegistrationService.setParams(paymentMethod, token);
		this.resourceData.setMap(this.paymentMethodExecuteRegistrationService.execute());

		return ResponseEntity.ok(this.resourceData);
	}
}