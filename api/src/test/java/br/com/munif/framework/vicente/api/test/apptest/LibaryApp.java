package br.com.munif.framework.vicente.api.test.apptest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@ComponentScan(basePackages = {"br.com.munif.framework.vicente.api.test.apptest",
    "br.com.munif.framework.vicente.api",
    "br.com.munif.framework.vicente.application.victenancyfields",
    "br.com.munif.framework.vicente.application"
})
@EnableAutoConfiguration( exclude = {MetricFilterAutoConfiguration.class, MetricRepositoryAutoConfiguration.class})
@EnableConfigurationProperties({LiquibaseProperties.class, ApplicationProperties.class})
@EntityScan(basePackages = {"br.com.munif.framework.vicente.domain","br.com.munif.framework.vicente.api.test.apptest"})
public class LibaryApp {

    private static final Logger log = LoggerFactory.getLogger(LibaryApp.class);

    private final Environment env;

    public LibaryApp(Environment env) {
        this.env = env;
    }

    @PostConstruct
    public void initApplication() {
        
    }

    public static void main(String[] args) throws UnknownHostException {
        SpringApplication app = new SpringApplication(LibaryApp.class);
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
            env.getProperty("server.port"),
            protocol,
            InetAddress.getLocalHost().getHostAddress(),
            env.getProperty("server.port"),
            env.getActiveProfiles());
    }
}
