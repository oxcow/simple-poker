package net.iyiguo.simplepoker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * 异步任务线程池配置
 *
 * @author leeyee
 * @date 2021/8/4
 */
@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {
    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(8);
        executor.setQueueCapacity(5);
        executor.setThreadNamePrefix("AsyncExecutor-");
        return executor;
    }

    @Override
    public Executor getAsyncExecutor() {
        return taskExecutor();
    }

}
