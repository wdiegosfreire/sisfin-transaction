package br.com.dfdevforge.sisfintransaction.commons.exceptions;

public class UniqueConstraintException extends BaseException implements HttpStatusBadRequest {
	private static final long serialVersionUID = 1L;

	public UniqueConstraintException() {
		super("Unique Constraint Violated", "This information already exists. Review the data and try again.");
	}
}