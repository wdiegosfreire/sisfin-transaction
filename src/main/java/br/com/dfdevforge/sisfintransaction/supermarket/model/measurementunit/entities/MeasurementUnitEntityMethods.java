package br.com.dfdevforge.sisfintransaction.supermarket.model.measurementunit.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class MeasurementUnitEntityMethods {
	private final MeasurementUnitEntity measurementUnit;

	public MeasurementUnitEntityMethods() {
		this.measurementUnit = null;
	}
}