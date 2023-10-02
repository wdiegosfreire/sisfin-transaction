package br.com.dfdevforge.sisfintransaction.commons.exceptions;

public class ChildrenInformationFoundException extends BaseException implements HttpStatusInternalServerError {
	private static final long serialVersionUID = 1L;

	public ChildrenInformationFoundException() {
		super("Children Information Found", "There are children information associated with this record.");
	}
}