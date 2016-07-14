package my.personal.config;

import my.personal.dao.destination.PriceDataDestinationDao;
import my.personal.dao.destination.jdbc.jdbcPriceDataDestinationDao;
import my.personal.dao.source.InstrumentsDao;
import my.personal.dao.source.MarketDataDao;
import my.personal.dao.source.file.FileInstrumentsDao;
import my.personal.dao.source.file.FileMarketDataDao;
import my.personal.service.PriceProcessingService;
import my.personal.service.PriceProcessingServiceImpl;
import my.personal.service.reader.ServiceReader;
import my.personal.service.reader.ServiceReaderImpl;
import my.personal.service.reporter.ServiceReporter;
import my.personal.service.reporter.ServiceReporterImpl;
import my.personal.service.writer.ServiceWriteImpl;
import my.personal.service.writer.ServiceWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

/**
 * Created by atifsaleem on 12/7/16.
 */
@Configuration
@PropertySource("classpath:/test.properties")
public class TestMarketDataServiceAppConfig {
    @Bean
    public InstrumentsDao getInstrumentsDao(){
        return new FileInstrumentsDao();
    }

    @Bean
    public MarketDataDao getMarketDataDao(){
        return new FileMarketDataDao();
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        EmbeddedDatabase db = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("db/sql/create-db.sql")
                .build();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(db);
        return jdbcTemplate;
    }
    @Bean
    public PriceDataDestinationDao getPriceDataDestinationDao(){
        jdbcPriceDataDestinationDao priceDataDestinationDao = new jdbcPriceDataDestinationDao();
        return priceDataDestinationDao;
    }
    @Bean
    public PriceProcessingService getPriceProcessingService() {
        return new PriceProcessingServiceImpl();
    }

    @Bean
    public ServiceWriter getServiceWriter() {
        return new ServiceWriteImpl();
    }
    @Bean
    public ServiceReader getServiceReader() {
        return new ServiceReaderImpl();
    }
    @Bean
    public ServiceReporter getServiceReporter() {
        return new ServiceReporterImpl();
    }

}
