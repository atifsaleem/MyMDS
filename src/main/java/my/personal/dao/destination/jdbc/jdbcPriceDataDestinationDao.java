package my.personal.dao.destination.jdbc;

import my.personal.dao.destination.PriceDataDestinationDao;
import my.personal.dao.source.file.FileInstrumentsDao;
import my.personal.domain.InstrumentPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by atifsaleem on 13/7/16.
 */
@Repository
public class jdbcPriceDataDestinationDao implements PriceDataDestinationDao{
    private static final Logger LOGGER = Logger.getLogger(jdbcPriceDataDestinationDao.class.getName());

    @Value("${insert.priceData.batch.sql}")
    private String insertPriceDataSql;
    @Value("${get.second.highest.persisted.price.per.ticker.sql}")
    private String secondHighestPriceSql;
    @Value("${get.average.persisted.price.over.last.ten.seconds.sql}")
    private String averagePriceOverLastTenSql;

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public boolean persistPriceData(final List<InstrumentPrice> prices) {
        for(InstrumentPrice instrumentPrice: prices)
            LOGGER.info("Persisting "+instrumentPrice.getSymbol());
        jdbcTemplate.batchUpdate(insertPriceDataSql, new BatchPreparedStatementSetter() {

            public void setValues(PreparedStatement ps, int i) throws SQLException {
                InstrumentPrice priceData = prices.get(i);
                ps.setString(1, priceData.getSymbol());
                ps.setDouble(2, priceData.getPrice());
                ps.setInt(3, priceData.getTime() );
            }

            public int getBatchSize() {
                return prices.size();
            }
        });
        return true;
    }

    public boolean executeQuery(String sql) {
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);
        return true;
    }

    public List<Map<String,Object>> getSecondHighestPricePerTicker() {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(secondHighestPriceSql);
        return rows;
    }

    public List<Map<String,Object>> getAveragePriceOverLastTenSeconds() {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(averagePriceOverLastTenSql);
        return rows;
    }
}
