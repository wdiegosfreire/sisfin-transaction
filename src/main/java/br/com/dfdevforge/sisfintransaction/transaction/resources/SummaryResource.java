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
import br.com.dfdevforge.sisfintransaction.transaction.entities.SummaryEntity;
import br.com.dfdevforge.sisfintransaction.transaction.services.summary.SummaryAccessModuleService;

@RestController
@RequestMapping(value = "/summary")
public class SummaryResource {
	private ResourceDataEntity resourceData = new ResourceDataEntity();

	private final SummaryAccessModuleService summaryAccessModuleService;

	@Autowired
	public SummaryResource(SummaryAccessModuleService summaryAccessModuleService) {
		this.summaryAccessModuleService = summaryAccessModuleService;
	}

	@PostMapping(value = "/accessModule")
	public ResponseEntity<ResourceDataEntity> accessModule(@RequestBody SummaryEntity summary, @RequestParam String token) throws BaseException {
		this.summaryAccessModuleService.setParams(summary, token);
		this.resourceData.setMap(this.summaryAccessModuleService.execute());

		return ResponseEntity.ok(this.resourceData);
	}
}