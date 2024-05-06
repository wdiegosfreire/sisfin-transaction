package br.com.dfdevforge.sisfintransaction.commons.configs;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
public class CorsConfig extends WebSecurityConfigurerAdapter {
	private static final String SEPARATOR = ", ";

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable();
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