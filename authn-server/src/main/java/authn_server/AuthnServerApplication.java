package authn_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author kiranchauhan
 * Main Class for AuthN Server
 */

/* @SpringBootApplication= @Configuration + @EnableAutoConfiguration + @ComponentScan

@Configuration= it indicates that this class is used by spring IOC container as a source of bean definition
@EnableAutoConfiguration = this annotation configures the bean that are present in the classpath
@ComponentScan = It scans for package & all its subpackages looking for classes that could be automatically registered as beans in spring container

 */
@SpringBootApplication
public class AuthnServerApplication {

    public static void main(String[] args) {SpringApplication.run(AuthnServerApplication.class, args);
    }

}
