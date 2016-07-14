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
 *
 * The method for running the application lives here. The scheduled method polls the market data source based on the polling interval defined in the properties
 */
@Configuration
@ComponentScan({ "my.personal.*" })
@PropertySource("classpath:common.properties")
@EnableScheduling
public class MarketDataServiceAppConfig {
    private static final Logger LOGGER = Logger.getLogger(MarketDataServiceAppConfig.class.getName());
    public static final int RETURN_CODE = 0;

    @Value("${ddl.path}")
    private String ddlPath;

    public void setDdlPath(String ddlPath) {
        this.ddlPath = ddlPath;
    }

    @Bean
    public JdbcTemplate getJdbcTemplate() {

        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        EmbeddedDatabase db = builder
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript(ddlPath)
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

    @Scheduled(fixedRateString = "${polling.interval}")
    public void run() {
        currentTime++;
        if (currentTime>maxExecutionTime)
        {   LOGGER.info("Price Process ended, publishing report");
            serviceReporter.publishAveragePriceOverLastTenSecondsReport();
            serviceReporter.publishSecondHighestPersistedReport();
            shutdownManager.initiateShutdown(RETURN_CODE);
        }
        else {
            serviceWriter.runWriter();
            serviceReader.runReader();
        }
    }
}