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

	@ExceptionHandler({
		DebugException.class,
		ForeignKeyConstraintException.class,
		ArrayIndexOutOfBoundsException.class,
		RequiredFieldNotFoundException.class,
		ChildrenInformationFoundException.class
	})
	public ResponseEntity<String> httpInternalServerErrorExceptionHandler(HttpStatusInternalServerError exception, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
	}

	@ExceptionHandler({UserUnauthorizedException.class})
	public ResponseEntity<String> httpUnauthorizedExceptionHandler(Exception exception, HttpServletRequest request) {
		Utils.log.stackTrace(exception);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(this.handleExceptionMessage(exception));
	}

	@ExceptionHandler({UniqueConstraintException.class, DataIntegrityViolationException.class})
	public ResponseEntity<String> httpBadRequestExceptionHandler(Exception exception, HttpServletRequest request) {
		Utils.log.stackTrace(exception);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(this.handleExceptionMessage(exception));
	}

	private String handleExceptionMessage(Exception exception) {
		String message = "";

		if (exception instanceof DataIntegrityViolationException && exception.getMessage().contains("constraint") && exception.getMessage().contains("un_"))
			message = new UniqueConstraintException().getMessage();
		else if (exception instanceof UserUnauthorizedException)
			message = exception.getMessage();
		else
			message = Utils.log.stackTrace(exception);

		return message;
	}
}