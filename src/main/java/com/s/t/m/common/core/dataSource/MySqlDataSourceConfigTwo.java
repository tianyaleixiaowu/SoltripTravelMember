package com.s.t.m.common.core.dataSource;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
/**
 * 多数据源，应用在mysql读写分离
 * @author Bai
 * 需要在配置文件中配置路径及连接池
 */
//@Configuration
//配置dao层所使用的数据源使用包路径分割(如com.libertad.demo.dao.db2)在dao层中db2区分使用第二个数据源
//@MapperScan(basePackages = {"com.libertad.demo.dao.db2"}, sqlSessionFactoryRef = "secondSqlSessionFactory")
public class MySqlDataSourceConfigTwo {

    @Bean(name = "secondDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.db2")//读取配置的前缀
    public DataSource dataSource() {
        return new DruidDataSource();
    }

    @Bean(name = "secondSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        Resource[] mapperLocations = new PathMatchingResourcePatternResolver().getResources("classpath:mapper/db2/*.xml");//TODO 配置mapper读取路径
        sessionFactory.setMapperLocations(mapperLocations);
        return sessionFactory.getObject();
    }
}

