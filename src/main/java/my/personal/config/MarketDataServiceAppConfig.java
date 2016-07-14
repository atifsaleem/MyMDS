package my.personal.config;

import my.personal.dao.destination.jdbc.jdbcPriceDataDestinationDao;
import my.personal.service.reader.ServiceReader;
import my.personal.service.reporter.ServiceReporter;
import my.personal.service.writer.ServiceWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.logging.Logger;

/**
 * Created by atifsaleem on 12/7/16.
 */
@Configuration
@ComponentScan({ "my.personal.*" })
@PropertySource("classpath:common.properties")
@EnableScheduling
public class MarketDataServiceAppConfig {
    private static final Logger LOGGER = Logger.getLogger(MarketDataServiceAppConfig.class.getName());

    @Bean
    public JdbcTemplate getJdbcTemplate() {

        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        EmbeddedDatabase db = builder
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("db/sql/create-db.sql")
                .build();

        return new JdbcTemplate(db);
    }
    @Autowired
    ServiceWriter serviceWriter;
    @Autowired
    ServiceReader serviceReader;
    @Autowired
    ServiceReporter serviceReporter;

    @Autowired
    ShutdownManager shutdownManager;

    @Value("${execution.time}")
    int maxExecutionTime;

    int currentTime=-1;

    @Scheduled(fixedRate = 1000)
    public void run() {
        currentTime++;
        if (currentTime>maxExecutionTime)
        {   LOGGER.info("Process ended, publishing report");
            serviceReporter.publishAveragePriceOverLastTenSecondsReport();
            serviceReporter.publishSecondHighestPersistedReport();
            shutdownManager.initiateShutdown(0);
        }
        else {
            serviceWriter.runWriter();
            serviceReader.runReader();
        }
    }
}