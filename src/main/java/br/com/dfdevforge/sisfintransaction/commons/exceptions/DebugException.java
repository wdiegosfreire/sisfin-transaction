package br.com.dfdevforge.sisfintransaction.commons.exceptions;

public class DebugException extends BaseException implements HttpStatusInternalServerError {
	private static final long serialVersionUID = 1L;

	public DebugException(String summary, String message) {
		super(summary, message);
	}
}