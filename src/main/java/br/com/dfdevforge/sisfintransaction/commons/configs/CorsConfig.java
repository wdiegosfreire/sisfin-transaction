package br.com.dfdevforge.sisfintransaction.commons.configs;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.UserUnauthorizedException;
import br.com.dfdevforge.sisfintransaction.commons.feignclients.UserFeignClient;
import br.com.dfdevforge.sisfintransaction.commons.security.TokenAuthenticationFilter;

@EnableWebSecurity
public class CorsConfig extends WebSecurityConfigurerAdapter {
	private static final String SEPARATOR = ", ";

	private static final String[] PUBLIC_PATHS = {
		"/imrunning/**",
		"/actuator/**",
		"/v3/api-docs/**",
		"/swagger-ui/**",
		"/swagger-ui.html",
		"/error"
	};

	private final UserFeignClient userFeignClient;

	@Autowired
	public CorsConfig(UserFeignClient userFeignClient) {
		this.userFeignClient = userFeignClient;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeRequests()
				.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
				.antMatchers(PUBLIC_PATHS).permitAll()
				.anyRequest().authenticated()
			.and()
			.exceptionHandling().authenticationEntryPoint(this.unauthorizedEntryPoint())
			.and()
			.addFilterBefore(new TokenAuthenticationFilter(this.userFeignClient), UsernamePasswordAuthenticationFilter.class);
	}

	/**
	 * Answers 401 with the same body produced by {@link UserUnauthorizedException}, as the frontend expects.
	 */
	private AuthenticationEntryPoint unauthorizedEntryPoint() {
		return (request, response, authException) -> {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.setCharacterEncoding(StandardCharsets.UTF_8.name());
			response.getWriter().write(new UserUnauthorizedException().getMessage());
		};
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		final String[] corsOrigins = System.getenv("SISFIN_BACKEND_CORS_ORIGINS").split(SEPARATOR);
		final String[] corsMethods = System.getenv("SISFIN_BACKEND_CORS_METHODS").split(SEPARATOR);
		final String[] corsHeaders = System.getenv("SISFIN_BACKEND_CORS_HEADERS").split(SEPARATOR);

		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList(corsOrigins));
		configuration.setAllowedMethods(Arrays.asList(corsMethods));
		configuration.setAllowedHeaders(Arrays.asList(corsHeaders));
		configuration.setAllowCredentials(false);

		UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
		corsConfigurationSource.registerCorsConfiguration("/**", configuration);

		return corsConfigurationSource;
	}
}