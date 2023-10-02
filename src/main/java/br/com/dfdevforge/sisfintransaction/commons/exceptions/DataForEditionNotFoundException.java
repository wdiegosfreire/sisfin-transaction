package br.com.dfdevforge.sisfintransaction.commons.exceptions;

public class DataForEditionNotFoundException extends BaseException implements HttpStatusNotFound {
	private static final long serialVersionUID = 1L;

	public DataForEditionNotFoundException() {
		super("Data Not Fount", "Unable to identify record to be edited.");
	}
}