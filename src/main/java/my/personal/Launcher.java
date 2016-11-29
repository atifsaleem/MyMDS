package my.personal;
import my.personal.config.ServiceInitiator;
import my.personal.config.MarketDataServiceAppConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by atifsaleem on 29/11/16.
 */
public class Launcher {
    public static void main(String[] args){
        start();
    }
    public static void start() {
        ApplicationContext ctx =
                new AnnotationConfigApplicationContext(MarketDataServiceAppConfig.class);

        ctx.getBean(ServiceInitiator.class).run();
    }

}
