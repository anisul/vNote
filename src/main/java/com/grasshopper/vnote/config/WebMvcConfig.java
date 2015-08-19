package com.grasshopper.vnote.config;

import com.grasshopper.vnote.Util.Utils;
import com.grasshopper.vnote.core.EmailSender;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Anisul on 8/14/2015.
 */

@Configuration
@EnableScheduling
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.grasshopper.vnote"})
@PropertySource({"classpath:app.properties"})
@ImportResource({"classpath:spring-security.xml"})
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Autowired
    private Environment env;

    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MappingJacksonHttpMessageConverter());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("css/");
        registry.addResourceHandler("/js/**").addResourceLocations("js/");
        registry.addResourceHandler("/lib/**").addResourceLocations("lib/");
        registry.addResourceHandler("/img/**").addResourceLocations("img/");
        registry.addResourceHandler("/partials/**").addResourceLocations("partials/");
    }

    @Bean
    public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler () {
        DefaultWebSecurityExpressionHandler o = new DefaultWebSecurityExpressionHandler();
        return o;
    }

    @Bean
    public DelegatingFilterProxy springSecurityFilterChain() {
        DelegatingFilterProxy filterProxy =  new DelegatingFilterProxy();
        return filterProxy;
    }

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    // Enable serving static resources even when DispatcherServlet is mapped to
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(1073741824); // Max size in bytes.
        return multipartResolver;
    }

    // Set up dataSource to be used by Hibernate. Also make sure the connection
    // doesn't go down
    @Bean
    public DataSource getDataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setUrl(env.getProperty("db.url"));
        ds.setDriverClassName(env.getProperty("db.driver"));
        ds.setUsername(env.getProperty("db.user"));
        ds.setPassword(env.getProperty("db.pass"));

        return ds;
    }

    //setup JPA and transactionManager
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(getDataSource());
        emf.setPackagesToScan("com.grasshopper.vnote.domain");

        Map<String, Object> opts = emf.getJpaPropertyMap();
        opts.put("hibernate.ejb.naming_strategy", "org.hibernate.cfg.ImprovedNamingStrategy");
        opts.put("hibernate.show_sql", env.getProperty("db.hibernate.show_sql"));
        opts.put("db.hibernate.hbm2ddl.auto", env.getProperty("db.hibernate.hbm2ddl.auto"));
        opts.put("db.hibernate.max_fetch_depth", env.getProperty("db.hibernate.max_fetch_depth"));

        HibernateJpaVendorAdapter va = new HibernateJpaVendorAdapter();
        emf.setJpaVendorAdapter(va);

        return emf;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager(entityManagerFactory().getObject());
    }


    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor o = new ThreadPoolTaskExecutor();
        o.setCorePoolSize(10);
        o.setMaxPoolSize(15);
        o.setQueueCapacity(25);
        return o;
    }
}
