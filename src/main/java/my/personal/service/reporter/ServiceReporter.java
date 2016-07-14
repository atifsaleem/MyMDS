package my.personal.service.reporter;

/**
 * Created by atifsaleem on 14/7/16.
 */
public interface ServiceReporter {
    void publishSecondHighestPersistedReport();
    void publishAveragePriceOverLastTenSecondsReport();
}
