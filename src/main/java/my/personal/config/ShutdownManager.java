package my.personal.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Created by atifsaleem on 14/7/16.
 * This component helps shutdown the application after the pre defined execution time
 */
@Component
public class ShutdownManager{

    @Autowired
    private ApplicationContext appContext;

    public void initiateShutdown(int returnCode){
        ((ConfigurableApplicationContext)appContext).close();
    }
}
