package com.magic.spring;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.postgresql.ds.PGSimpleDataSource;
import javax.sql.DataSource;

/**
 * @author Hsy
 * @Description We use this class to configure the database.
 */
@Configuration
@MapperScan("com/magic/spring/DAO/mapper")
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, MybatisAutoConfiguration.class})
public class PersistenceConfig {
    /**
     * It is better to configure the database with classes than with application.properties.
     * We can just use the @Value annotation to get the value from environment variables.
     * This way can protect our username and password of database.
     * And we don't need to be worried about when we push the codes,
     * we will change other developers' username and password :-).
     * About how to set the environment variables in idea:
     * https://blog.csdn.net/u010180815/article/details/105219332
     */
    @Value("${POSTGRES_URL}")
    private String postgresURL;
    @Value("${POSTGRES_USER}")
    private String postgresUser;
    @Value("${POSTGRES_PASSWORD}")
    private String postgresPassword;

    @Bean
    public DataSource dataSource() {
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setUrl(postgresURL);
        ds.setUser(postgresUser);
        ds.setPassword(postgresPassword);
        ds.setSslMode("disable");
        return ds;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource());
        return factoryBean.getObject();
    }

}
