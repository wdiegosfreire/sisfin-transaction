package br.com.dfdevforge.sisfintransaction.transaction.model.location.services;

import br.com.dfdevforge.sisfintransaction.commons.services.BaseService;
import br.com.dfdevforge.sisfintransaction.transaction.model.location.entities.LocationEntity;

public abstract class LocationBaseService extends BaseService {
	protected LocationEntity locationParam;

	public void setParams(LocationEntity locationEntity) {
		this.locationParam = locationEntity;
	}
}
