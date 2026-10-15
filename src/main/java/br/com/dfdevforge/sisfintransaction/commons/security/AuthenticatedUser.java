package br.com.dfdevforge.sisfintransaction.commons.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.dfdevforge.sisfintransaction.commons.entities.UserEntity;

public final class AuthenticatedUser {
	private AuthenticatedUser() {}

	public static UserEntity get() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null && authentication.getPrincipal() instanceof UserEntity)
			return (UserEntity) authentication.getPrincipal();

		return null;
	}

	public static Long getIdentity() {
		UserEntity user = get();
		return user == null ? null : user.getIdentity();
	}
}
