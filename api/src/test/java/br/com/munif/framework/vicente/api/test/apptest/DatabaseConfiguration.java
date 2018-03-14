package br.com.munif.framework.vicente.api.test.apptest;

import br.com.munif.framework.vicente.application.VicRepositoryImpl;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableJpaRepositories(basePackages ={"br.com.munif.framework.vicente.api.test.apptest","br.com.munif.framework.vicente.application.victenancyfields"},repositoryBaseClass = VicRepositoryImpl.class)
@EnableTransactionManagement
public class DatabaseConfiguration {

    private final Logger log = LoggerFactory.getLogger(DatabaseConfiguration.class);

    private final Environment env;

    public DatabaseConfiguration(Environment env) {
        this.env = env;
    }

}
