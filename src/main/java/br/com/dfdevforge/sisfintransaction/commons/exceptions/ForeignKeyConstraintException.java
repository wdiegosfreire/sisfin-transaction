package br.com.dfdevforge.sisfintransaction.commons.exceptions;

public class ForeignKeyConstraintException extends BaseException implements HttpStatusInternalServerError {
	private static final long serialVersionUID = 1L;

	public ForeignKeyConstraintException() {
		super("Foreign Key Constraint Violated", "This information has child records and can't be deleted.");
	}
}