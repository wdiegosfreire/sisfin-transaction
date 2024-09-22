package br.com.dfdevforge.sisfintransaction.statement.entities;

import java.util.List;

import br.com.dfdevforge.sisfintransaction.transaction.entities.ObjectiveMovementEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatementItemEntityProps {
	private List<ObjectiveMovementEntity> objectiveMovementList;
}