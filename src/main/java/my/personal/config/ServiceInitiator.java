package my.personal.config;

import my.personal.service.reader.ServiceReader;
import my.personal.service.reporter.ServiceReporter;
import my.personal.service.writer.ServiceWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;
import java.util.logging.Logger;

/**
 * Created by atifsaleem on 12/7/16.
 * Entry point for the application. Initializes app context
 */
@Component
@PropertySource("classpath:common.properties")
public class ServiceInitiator implements Runnable{
    private static final Logger LOGGER = Logger.getLogger(ServiceInitiator.class.getName());
    public static final int RETURN_CODE = 0;

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

    public void run() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
        final Future handler1 = executor.submit(serviceReader);
        final Future handler2 =  executor.submit(serviceWriter);
        try {
            System.out.println("Started..");
            System.out.println(handler1.get(maxExecutionTime+1, TimeUnit.SECONDS));
            System.out.println("Finished!");
        } catch (TimeoutException e) {
            handler1.cancel(true);
            handler2.cancel(true);
            executor.shutdownNow();
            System.out.println("Terminated!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        serviceReporter.run();
        shutdownManager.initiateShutdown(0);
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}

