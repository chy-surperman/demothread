package com.chy.task;

        import org.springframework.context.annotation.Configuration;
        import org.springframework.scheduling.annotation.EnableScheduling;
        import org.springframework.scheduling.annotation.Scheduled;

        import java.time.LocalDateTime;

@Configuration
@EnableScheduling
public class scheduleController {
    @Scheduled(cron="0/5 * * * * ?")
    private void configureTasks() {
        System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
    }
}
