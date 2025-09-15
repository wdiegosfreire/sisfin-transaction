package br.com.dfdevforge.sisfintransaction.statement.model.statementitem.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class StatementItemEntityMethods {
	private final StatementItemEntity statementItem;

	public boolean isIncoming() {
		return this.statementItem.getOperationType().equalsIgnoreCase("C");
	}

	public boolean isOutcoming() {
		return this.statementItem.getOperationType().equalsIgnoreCase("D");
	}
}