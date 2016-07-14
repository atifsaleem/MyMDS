package my.personal;

import my.personal.config.MarketDataServiceAppConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by atifsaleem on 12/7/16.
 * Entry point for the application. Initializes app context
 */
public class Launcher {
    public static void main(String[] args) {
        ApplicationContext ctx =
                new AnnotationConfigApplicationContext(MarketDataServiceAppConfig.class);

    }
}
