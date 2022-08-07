package pdtg.lsmscbeersrvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by Diego T. 07-08-2022
 */
@Configuration
@EnableAsync
@EnableScheduling
public class TaskConfig {

    @Bean
    TaskExecutor taskExecutor(){
        return new SimpleAsyncTaskExecutor();
    }
}
