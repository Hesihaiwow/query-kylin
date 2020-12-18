package com.zhixinhuixue.querykylin.config;

import com.zhixinhuixue.querykylin.datasource.KylinDataSource;
import com.zhixinhuixue.querykylin.entity.KylinProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * kylin 配置
 *
 * @author hsh
 * @create 2020年12月17日
 */
@Configuration
public class KylinConfig {

    @Bean(name = "kylinProperties")
    @ConfigurationProperties(prefix = "spring.datasource.kylin")
    public KylinProperties creatKylinProperties() {
        return new KylinProperties();
    }

    @Bean(name = "kylinDataSource")
    public DataSource KylinDataSource(@Qualifier("kylinProperties") KylinProperties kylinProperties) {
        return new KylinDataSource(kylinProperties);
    }

    @Bean(name = "kylinTemplate")
    public JdbcTemplate prestoJdbcTemplate(@Qualifier("kylinDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}