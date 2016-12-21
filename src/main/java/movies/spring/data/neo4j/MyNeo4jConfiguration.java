package movies.spring.data.neo4j;

import org.neo4j.ogm.session.SessionFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.neo4j.Neo4jProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.repository.config.EnableExperimentalNeo4jRepositories;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.data.neo4j.web.support.OpenSessionInViewInterceptor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

// tag::config[]
@ComponentScan(basePackages = {"movies.spring.data.neo4j.services"})
@Configuration
@EnableExperimentalNeo4jRepositories(basePackages = {"movies.spring.data.neo4j.repositories", "movies.spring.data.neo4j.domain"})
@EnableConfigurationProperties(Neo4jProperties.class)
public class MyNeo4jConfiguration extends WebMvcConfigurerAdapter {

//    public static final String URL = System.getenv("NEO4J_URL") != null ? System.getenv("NEO4J_URL") : "http://neo4j:123456@192.168.79.128:7474";
//
//    @Bean
//    public org.neo4j.ogm.config.Configuration getConfiguration() {
//        org.neo4j.ogm.config.Configuration config = new org.neo4j.ogm.config.Configuration();
//        config
//                .driverConfiguration()
//                .setDriverClassName("org.neo4j.ogm.drivers.http.driver.HttpDriver")
//                .setURI(URL);
//        return config;
//    }

  @Bean
  public SessionFactory sessionFactory() {
    // with domain entity base package(s)
    return new SessionFactory("movies.spring.data.neo4j.domain", "BOOT-INF.classes.movies.spring.data.neo4j.domain");
  }

  @Bean
  public Neo4jTransactionManager transactionManager() {
    return new Neo4jTransactionManager(sessionFactory());
  }

  //    @Override
//    public SessionFactory getSessionFactory() {
//        return new SessionFactory( "movies.spring.data.neo4j.domain","BOOT-INF.movies.spring.data.neo4j.domain");
//    }
  @Bean
  public OpenSessionInViewInterceptor openSessionInViewInterceptor() {
    OpenSessionInViewInterceptor openSessionInViewInterceptor =
        new OpenSessionInViewInterceptor();
    openSessionInViewInterceptor.setSessionFactory(sessionFactory());
    return openSessionInViewInterceptor;
  }
}
// end::config[]
