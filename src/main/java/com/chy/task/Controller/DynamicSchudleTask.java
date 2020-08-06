package com.chy.task.Controller;

import com.chy.task.Entities.cron;
import com.chy.task.Mappers.cronMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.SimpleTriggerContext;

import java.time.LocalDateTime;
import java.util.Date;

public class DynamicSchudleTask  implements SchedulingConfigurer {

    @Autowired
    @SuppressWarnings("all")
    cronMapper cronMapper;

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.addCronTask(new Runnable() {
            @Override
            public void run() {
                // 定时任务要执行的内容
                System.out.println("【开始执行定时任务。。。】");
            }
        },
        String.valueOf(new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                // 定时任务触发，可修改定时任务的执行周期
                String cron = "0 0/5 * * * ?"; //可以将表达式配置在数据库中
                CronTrigger trigger = new CronTrigger(cron);
                Date nextExecDate = trigger.nextExecutionTime(triggerContext);
                return nextExecDate;
            }
        }));
    }
}
