package br.com.dfdevforge.sisfintransaction.commons.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.dfdevforge.sisfintransaction.commons.utils.Utils;

@ControllerAdvice
public class ResourceExceptionHandler {
	@ExceptionHandler({DataForEditionNotFoundException.class, DataForExclusionNotFoundException.class})
	public ResponseEntity<String> httpNotFoundExceptionHandler(HttpStatusNotFound exception, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
	}

	@ExceptionHandler({Exception.class})
	public ResponseEntity<String> exceptionHandler(Exception exception, HttpServletRequest request) {
		Utils.log.stackTrace(exception);
		return this.handleExceptionMessage(exception);
	}

	private ResponseEntity<String> handleExceptionMessage(Exception exception) {
		ResponseEntity<String> responseEntity = null;

		if (exception instanceof DataIntegrityViolationException) {
			if (exception.getCause().getCause().getMessage().contains("Duplicate entry"))
				responseEntity = new ResponseEntity<>(new UniqueConstraintException().getMessage(), HttpStatus.BAD_REQUEST);
			else if (exception.getCause().getCause().getMessage().contains("Cannot delete or update a parent row"))
				responseEntity = new ResponseEntity<>(new ForeignKeyConstraintException().getMessage(), HttpStatus.BAD_REQUEST);
		}
		else if (exception instanceof UserUnauthorizedException) {
			responseEntity = new ResponseEntity<>(exception.getMessage(), HttpStatus.UNAUTHORIZED);
		}
		else {
			responseEntity = new ResponseEntity<>(Utils.log.stackTrace(exception), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return responseEntity;
	}
}