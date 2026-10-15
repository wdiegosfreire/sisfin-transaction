package br.com.dfdevforge.sisfintransaction.commons.security;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.dfdevforge.sisfintransaction.commons.entities.UserEntity;
import br.com.dfdevforge.sisfintransaction.commons.feignclients.UserFeignClient;
import feign.FeignException;

public class TokenAuthenticationFilter extends OncePerRequestFilter {
	private static final String BEARER_PREFIX = "Bearer ";
	private static final String LEGACY_TOKEN_PARAM = "token";

	private final UserFeignClient userFeignClient;

	public TokenAuthenticationFilter(UserFeignClient userFeignClient) {
		this.userFeignClient = userFeignClient;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String token = this.extractToken(request);

		if (token != null) {
			try {
				UserEntity user = this.userFeignClient.validateToken(token);

				if (user != null && user.getIdentity() != null) {
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, token, Collections.emptyList());
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
			catch (FeignException e) {
				SecurityContextHolder.clearContext();
			}
		}

		filterChain.doFilter(request, response);
	}

	private String extractToken(HttpServletRequest request) {
		String header = request.getHeader(HttpHeaders.AUTHORIZATION);

		if (header != null && header.startsWith(BEARER_PREFIX)) {
			String token = header.substring(BEARER_PREFIX.length()).trim();

			if (StringUtils.isNotBlank(token))
				return token;
		}

		String legacyToken = request.getParameter(LEGACY_TOKEN_PARAM);
		return StringUtils.isNotBlank(legacyToken) ? legacyToken : null;
	}
}
