package br.com.munif.framework.vicente.security;

import br.com.munif.framework.vicente.application.VicRepositoryImpl;
import br.com.munif.framework.vicente.security.config.ApplicationProperties;
import br.com.munif.framework.vicente.security.config.DefaultProfileUtil;
import br.com.munif.framework.vicente.security.seed.SeedSecurity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;

//import br.com.munif.framework.vicente.security.seed.SeedSecurity;

@ComponentScan(basePackages = {
        "br.com.munif.framework.vicente.security.api",
        "br.com.munif.framework.vicente.security.seed",
        "br.com.munif.framework.vicente.security.service",
        "br.com.munif.framework.vicente.security.config",
        "br.com.munif.framework.vicente.api.errors",
        "br.com.munif.framework.vicente.application.victenancyfields",
        "br.com.munif.framework.vicente.application"
})
@EnableAutoConfiguration()
@EnableJpaRepositories(basePackages = {
        "br.com.munif.framework.vicente.security.repository",
        "br.com.munif.framework.vicente.application.victenancyfields"
},
        repositoryBaseClass = VicRepositoryImpl.class)
@EnableConfigurationProperties({LiquibaseProperties.class, ApplicationProperties.class})
@EntityScan(basePackages = {
        "br.com.munif.framework.vicente.domain",
        "br.com.munif.framework.vicente.security.domain"
})
public class SecurityApp {

    private static final Logger log = LoggerFactory.getLogger(SecurityApp.class);

    private final Environment env;

    @Autowired
    private SeedSecurity seedSecurity;

    public SecurityApp(Environment env) {
        this.env = env;
    }

    public static void main(String[] args) throws UnknownHostException {
        SpringApplication app = new SpringApplication(SecurityApp.class);
        DefaultProfileUtil.addDefaultProfile(app);
        Environment env = app.run(args).getEnvironment();
        String protocol = "http";
        if (env.getProperty("server.ssl.key-store") != null) {
            protocol = "https";
        }
        log.info("\n----------------------------------------------------------\n\t" +
                        "Application '{}' is running! Access URLs:\n\t" +
                        "Local: \t\t{}://localhost:{}\n\t" +
                        "External: \t{}://{}:{}\n\t" +
                        "Profile(s): \t{}\n----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                protocol,
                env.getProperty("local.server.port"),
                protocol,
                InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("remote.server.port"),
                env.getActiveProfiles());
    }

    @PostConstruct
    public void initApplication() {
        seedSecurity.seedSecurity();
    }
}
