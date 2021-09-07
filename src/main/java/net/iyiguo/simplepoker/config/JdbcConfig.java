package net.iyiguo.simplepoker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.relational.core.dialect.Dialect;
import org.springframework.data.relational.core.dialect.MySqlDialect;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;

import javax.sql.DataSource;

/**
 * @author leeyee
 * @date 2021/08/30
 */
@Configuration(proxyBeanMethods = false)
@EnableJdbcRepositories("net.iyiguo.simplepoker.dao")
public class JdbcConfig extends AbstractJdbcConfiguration {

//    @Bean
//    public DataSource dataSource() {
//        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
//        return builder.setType(EmbeddedDatabaseType.H2).build();
//    }

    @Bean
    public Dialect jdbcDialect(NamedParameterJdbcOperations operations) {
        // H2Dialect.INSTANCE 使用的默认大写字段 @see H2Dialect.getIdentifierProcessing
        // 因此需要对于大小写敏感的H2数据库来说，如下的配置会导致SQL异常
        // DATABASE_TO_UPPER=false;MODE=MYSQL
        // 因此这种情况下使用MySqlDialect
        return MySqlDialect.INSTANCE; // H2Dialect.INSTANCE;
    }

    @Bean
    NamedParameterJdbcOperations namedParameterJdbcOperations(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    TransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
