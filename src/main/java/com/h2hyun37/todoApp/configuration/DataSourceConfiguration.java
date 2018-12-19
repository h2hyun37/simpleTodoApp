package com.h2hyun37.todoApp.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Setter;
import org.h2.tools.Server;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
@EnableTransactionManagement
@EnableJpaRepositories(basePackages="com.h2hyun37.todoApp.repository")
public class DataSourceConfiguration {
	@Setter
	private String username;

	@Setter
	private String password;

	@Setter
	private String url;

	@Setter
	private String driverClassName;

	@Setter
	private long connectionTimeout;

	@Setter
	private int maximumPoolSize;


	@Bean
	public Server h2embeddedServer() throws SQLException {
		Server h2embeddedServer = Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "8043").start();

		return h2embeddedServer;
	}

	@Bean
	public DataSource dataSource(Server server) throws SQLException {
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl(url);
		config.setUsername(username);
		config.setPassword(password);
		config.setDriverClassName(driverClassName);
		config.setMaximumPoolSize(maximumPoolSize);
		config.setConnectionTimeout(connectionTimeout);

		return new HikariDataSource(config);
	}
}
