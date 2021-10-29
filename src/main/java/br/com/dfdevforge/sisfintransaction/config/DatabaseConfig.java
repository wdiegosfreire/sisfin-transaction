package br.com.dfdevforge.sisfintransaction.config;

import java.net.URI;
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
		@SuppressWarnings("rawtypes")
		DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();

		URI jdbcUri = new URI(System.getenv("DATABASE_SISFIN_TRANSACTION"));

		dataSourceBuilder.driverClassName(CLASS_NAME);
		dataSourceBuilder.username(jdbcUri.getUserInfo().split(":")[0]);
		dataSourceBuilder.password(jdbcUri.getUserInfo().split(":")[1]);
		dataSourceBuilder.url("jdbc:mysql://" + jdbcUri.getHost() + ":" + jdbcUri.getPort() + jdbcUri.getPath());

        return dataSourceBuilder.build();
	}
}
