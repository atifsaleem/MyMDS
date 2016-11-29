package my.personal.service.reporter;

/**
 * Created by atifsaleem on 14/7/16.
 * Runs the reports after the service execution has ended
 */
public interface ServiceReporter extends Runnable{
    void publishSecondHighestPersistedReport();
    void publishAveragePriceOverLastTenSecondsReport();
}
