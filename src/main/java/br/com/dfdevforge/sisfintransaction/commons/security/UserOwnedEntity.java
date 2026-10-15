package br.com.dfdevforge.sisfintransaction.commons.security;

/**
 * Marks an entity that belongs to a user. When an entity implementing this interface is
 * received as a request body, {@link UserOwnershipRequestBodyAdvice} checks that it belongs
 * to the authenticated user, so no per-entity validation is needed.
 */
public interface UserOwnedEntity {
	Long getUserIdentity();
}
