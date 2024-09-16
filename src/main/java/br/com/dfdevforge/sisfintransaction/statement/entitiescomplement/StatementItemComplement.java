package br.com.dfdevforge.sisfintransaction.statement.entitiescomplement;

import java.util.List;

import br.com.dfdevforge.sisfintransaction.transaction.entities.ObjectiveMovementEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatementItemComplement {
	private List<ObjectiveMovementEntity> objectiveMovementList;
}