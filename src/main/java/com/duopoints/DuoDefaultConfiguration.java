package com.duopoints;

import org.jooq.SQLDialect;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.DefaultExecuteListenerProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
public class DuoDefaultConfiguration {

    private final Environment env;

    @Autowired
    public DuoDefaultConfiguration(Environment env) {
        this.env = env;
    }

    /**
     * Create the Primary PostgreSQL DataSource.
     */
    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource postgresqlDataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * Configure the LazyConnectionDataSourceProxy bean.
     * This bean ensures that the database connection are fetched lazily (i.e. when the first statement is created).
     */
    @Bean
    public LazyConnectionDataSourceProxy lazyConnectionDataSource() {
        return new LazyConnectionDataSourceProxy(postgresqlDataSource());
    }

    /**
     * Configure the TransactionAwareDataSourceProxy bean.
     * This bean ensures that all JDBC connection are aware of Spring-managed transactions.
     * In other words, JDBC connections participate in thread-bound transactions.
     */
    @Bean
    public TransactionAwareDataSourceProxy transactionAwareDataSource() {
        return new TransactionAwareDataSourceProxy(lazyConnectionDataSource());
    }

    /**
     * Configure the DataSourceTransactionManager bean.
     * We must pass the LazyConnectionDataSourceProxy bean as as constructor argument when
     * we create a new DataSourceTransactionManager object.
     */
    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(lazyConnectionDataSource());
    }

    /**
     * Configure the DataSourceConnectionProvider bean.
     * jOOQ will get the used connections from the DataSource given as a constructor argument.
     * We must pass the TransactionAwareDataSourceProxy bean as a constructor argument when we create
     * a new DataSourceConnectionProvider object. This ensures that the queries created by jOOQ participate in
     * Spring-managed transactions.
     */
    @Bean
    public DataSourceConnectionProvider connectionProvider() {
        return new DataSourceConnectionProvider(transactionAwareDataSource());
    }

    /**
     * Configure the JOOQToSpringExceptionTransformer bean. This transforms jOOQ exceptions into Spring exceptions.
     */
    @Bean
    public JooqExceptionTransformer jooqExceptionTransformer() {
        return new JooqExceptionTransformer();
    }

    @Bean
    public DefaultConfiguration configuration() {
        DefaultConfiguration jooqConfiguration = new DefaultConfiguration();

        jooqConfiguration.set(connectionProvider());
        jooqConfiguration.set(new DefaultExecuteListenerProvider(jooqExceptionTransformer()));

        String sqlDialectName = env.getRequiredProperty("spring.jooq.sql-dialect");
        SQLDialect dialect = SQLDialect.valueOf(sqlDialectName.toUpperCase());
        jooqConfiguration.set(dialect);

        return jooqConfiguration;
    }

    /**
     * Configure the DefaultDSLContext bean. We use this bean when we are creating database queries with jOOQ.
     */
    @Bean
    public DefaultDSLContext dsl() {
        return new DefaultDSLContext(configuration());
    }
}