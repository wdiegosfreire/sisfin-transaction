package br.com.dfdevforge.sisfintransaction.commons.exceptions;

import org.springframework.http.HttpStatus;

public class UserUnauthorizedException extends BaseException implements HttpStatusUnauthorized {
	private static final long serialVersionUID = 1L;

	public UserUnauthorizedException() {
		super("User Unauthorized", "Please, log in again.");
	}

	public HttpStatus getHttpStatus() {
		return HttpStatus.UNAUTHORIZED;
	}
}