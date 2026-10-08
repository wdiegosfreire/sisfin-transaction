package br.com.dfdevforge.sisfintransaction.commons.security;

import java.lang.reflect.Type;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

@ControllerAdvice
public class UserOwnershipRequestBodyAdvice extends RequestBodyAdviceAdapter {
	@Override
	public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
		return UserOwnedEntity.class.isAssignableFrom(methodParameter.getParameterType());
	}

	@Override
	public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
		Long authenticatedUserIdentity = AuthenticatedUser.getIdentity();
		Long entityUserIdentity = ((UserOwnedEntity) body).getUserIdentity();

		if (authenticatedUserIdentity == null || !authenticatedUserIdentity.equals(entityUserIdentity))
			throw new AccessDeniedException("The entity does not belong to the authenticated user.");

		return body;
	}
}
