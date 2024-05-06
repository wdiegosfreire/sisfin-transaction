package br.com.dfdevforge.sisfintransaction.commons.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceExceptionHandler {
	@ExceptionHandler({DataForEditionNotFoundException.class, DataForExclusionNotFoundException.class})
	public ResponseEntity<String> httpNotFoundExceptionHandler(HttpStatusNotFound exception, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
	}

	@ExceptionHandler({RequiredFieldNotFoundException.class, ChildrenInformationFoundException.class, ForeignKeyConstraintException.class, DataIntegrityViolationException.class, ArrayIndexOutOfBoundsException.class})
	public ResponseEntity<String> httpInternalServerErrorExceptionHandler(HttpStatusInternalServerError exception, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
	}

	@ExceptionHandler({UserUnauthorizedException.class})
	public ResponseEntity<String> httpUnauthorizedExceptionHandler(HttpStatusUnauthorized exception, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.getMessage());
	}

	@ExceptionHandler({UniqueConstraintException.class})
	public ResponseEntity<String> httpBadRequestExceptionHandler(HttpStatusBadRequest exception, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
	}
}