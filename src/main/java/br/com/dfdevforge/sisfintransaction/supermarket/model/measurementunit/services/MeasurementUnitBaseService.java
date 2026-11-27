package br.com.dfdevforge.sisfintransaction.supermarket.model.measurementunit.services;

import br.com.dfdevforge.sisfintransaction.commons.services.BaseService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.measurementunit.entities.MeasurementUnitEntity;

public abstract class MeasurementUnitBaseService extends BaseService {
	protected MeasurementUnitEntity measurementUnitParam;

	public void setParams(MeasurementUnitEntity measurementUnitEntity) {
		this.measurementUnitParam = measurementUnitEntity;
	}
}