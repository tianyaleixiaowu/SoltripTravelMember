package com.s.t.m.common.core.dataSource;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
/**
 * 多数据源，应用在mysql读写分离(主数据源配置写,主要新增、删除、修改)
 * @author Bai
 *需要在配置文件中配置路径及连接池
 *----dao层
 *com
 *	|>libertad
 *		|>demo
 *			|>dao
 *				|>db1
 *				|>db2
 *---mapper层
 *mapper
 *	|>mybatis
 *		|>db1
 *		|>db2
 */
//@Configuration
//@EnableTransactionManagement
//配置dao层所使用的数据源使用包路径分割(如com.libertad.demo.dao.db1)在dao层中使用db1区分使用第一个数据源
//@MapperScan(basePackages = {"com.libertad.demo.dao.db1"})
public class MySqlDataSourceConfigOne {

    @Bean(name = "primaryDataSource")
    //需设置主数据源
    @Primary
    @ConfigurationProperties(prefix="spring.datasource.db1")//读取配置的前缀
    public DataSource dataSource(){
        //跟之前不一样了
        return new DruidDataSource();
    }

    @Bean(name = "primaryTransactionManager")
    @Primary
    public DataSourceTransactionManager masterTransactionManager() {
        return new DataSourceTransactionManager(dataSource());

    }

    @Bean(name="primarySqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("primaryDataSource")DataSource primaryDataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(primaryDataSource);
        Resource[] mapperLocations = new PathMatchingResourcePatternResolver().getResources("classpath:mapper/db1/*.xml");//TODO 配置mapper读取路径
        sessionFactory.setMapperLocations(mapperLocations);
        return sessionFactory.getObject();
    }
}

