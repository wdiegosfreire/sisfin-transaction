package br.com.dfdevforge.sisfintransaction.transaction.model.objective.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ObjectiveEntityMethods {
	private final ObjectiveEntity objectiveItem;

	public ObjectiveEntityMethods() {
		this.objectiveItem = null;
	}

	public boolean isInstallmentPlan() {
		return this.objectiveItem.getInstallmentAmount() > 1;
	}
}