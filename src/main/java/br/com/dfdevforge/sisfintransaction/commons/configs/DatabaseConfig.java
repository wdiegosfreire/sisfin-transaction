package br.com.dfdevforge.sisfintransaction.commons.configs;

import java.net.URISyntaxException;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfig {
	private static final String CLASS_NAME = "com.mysql.cj.jdbc.Driver";

	@Bean
	public DataSource getDataSource() throws URISyntaxException {
		final String BASE = System.getenv("SISFIN_DATABASE_TRANSACTION_BASE");
		final String USER = System.getenv("SISFIN_DATABASE_TRANSACTION_USER");
		final String PASS = System.getenv("SISFIN_DATABASE_TRANSACTION_PASS");
		final String HOST = System.getenv("SISFIN_DATABASE_DEFAULT_HOST");
		final String PORT = System.getenv("SISFIN_DATABASE_DEFAULT_PORT");

		@SuppressWarnings("rawtypes")
		DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();

		dataSourceBuilder.driverClassName(CLASS_NAME);
		dataSourceBuilder.username(USER);
		dataSourceBuilder.password(PASS);
		dataSourceBuilder.url("jdbc:mysql://" + HOST + ":" + PORT + "/" + BASE);

        return dataSourceBuilder.build();
	}
}
