package br.com.dfdevforge.sisfintransaction.statement.model.statementitem.entities;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatementItemEntityProps {
	private List<String> similarMovementList = new ArrayList<>();
}