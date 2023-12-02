package br.com.dfdevforge.sisfintransaction.commons.exceptions;

public class DataForExclusionNotFoundException extends BaseException implements HttpStatusNotFound {
	private static final long serialVersionUID = 1L;

	public DataForExclusionNotFoundException() {
		super("Data Not Fount", "Unable to identify record to be excluded.");
	}
}