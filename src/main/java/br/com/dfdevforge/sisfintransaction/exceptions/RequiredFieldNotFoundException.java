package br.com.dfdevforge.sisfintransaction.exceptions;

import java.util.List;

import br.com.dfdevforge.common.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.HttpStatusInternalServerError;

public class RequiredFieldNotFoundException extends BaseException implements HttpStatusInternalServerError {
	private static final long serialVersionUID = 1L;

	public RequiredFieldNotFoundException() {
		super("Required Field Not", "There are empty required fields. Please, enter all required fields.");
	}

	public RequiredFieldNotFoundException(String summary, List<String> messageList) {
		super(summary, messageList);
	}
}