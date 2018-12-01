package com.gordeev.movieland.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableTransactionManagement
public class ServiceImplConfig {

    private DataSource dataSource;
    private long enrichTimeout;

    @Autowired
    public ServiceImplConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    }

    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        return new ThreadPoolExecutor(3,3, enrichTimeout, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
    }

    @Value("${enrichMovie.timeOut}")
    public void setEnrichTimeout(long enrichTimeout) {
        this.enrichTimeout = enrichTimeout;
    }
}
