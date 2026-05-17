package br.com.dfdevforge.sisfintransaction.statement.model.statement.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatementEntityProps {
	private Boolean isNewHeader;
	private Boolean isAddInstallment;
	private Boolean isCreateObjective;

	private Long objectiveIdentity;
}