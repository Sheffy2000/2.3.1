package web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableWebMvc
@PropertySource("classpath:db.properties")
@ComponentScan("web")
@EnableTransactionManagement
public class WebConfig implements WebMvcConfigurer {

    private final ApplicationContext applicationContext;

    public WebConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    //DatBase
    @Autowired
    private Environment env;

    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource ();
        dataSource.setDriverClassName (env.getProperty ("db.driver"));
        dataSource.setUrl (env.getProperty ("db.url"));
        dataSource.setUsername (env.getProperty ("db.username"));
        dataSource.setPassword (env.getProperty ("db.password"));
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean getEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean ();
        factoryBean.setDataSource (getDataSource ());
        factoryBean.setPackagesToScan ("web.model");
        factoryBean.setJpaVendorAdapter (new HibernateJpaVendorAdapter ());

        Properties jpaProperties = new Properties ();
        jpaProperties.put ("hibernate.show_sql", env.getProperty ("hibernate.show_sql"));
        jpaProperties.put ("hibernate.hbm2ddl.auto", env.getProperty ("hibernate.hbm2ddl.auto"));

        factoryBean.setJpaProperties (jpaProperties);
        return factoryBean;
    }

    @Bean
    public JpaTransactionManager getTransactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager (emf);
    }

    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver ();
        templateResolver.setApplicationContext (applicationContext);
        templateResolver.setPrefix ("/WEB-INF/pages/");
        templateResolver.setSuffix (".html");
        templateResolver.setCharacterEncoding ("UTF-8");
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine ();
        templateEngine.setTemplateResolver (templateResolver ());
        templateEngine.setEnableSpringELCompiler (true);
        return templateEngine;
    }


    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver ();
        resolver.setTemplateEngine (templateEngine ());
        resolver.setCharacterEncoding ("UTF-8");
        resolver.setContentType ("text/html; charset=UTF-8");
        registry.viewResolver (resolver);
    }

    @Bean
    public CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter ();
        filter.setEncoding ("UTF-8"); // Устанавливаем кодировку
        filter.setForceEncoding (true); // Принудительно используем UTF-8
        return filter;
    }

}
