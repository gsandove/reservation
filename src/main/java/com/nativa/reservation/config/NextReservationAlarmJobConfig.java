package com.nativa.reservation.config;

import com.nativa.reservation.quartz.reservations.NextReservationAlarmJob;
import jakarta.annotation.PostConstruct;
import org.quartz.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;


@Configuration
public class NextReservationAlarmJobConfig {

    private final ApplicationContext applicationContext;

    public NextReservationAlarmJobConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void init(){
        System.out.println("Hello Quartz...");
    }

    @Bean
    public JobDetail buildJobDetailNexReservationAlarm(){
        return JobBuilder
                .newJob(NextReservationAlarmJob.class)
                .withIdentity("Next_Reservation_Alarm_Job1","my_job_1")
                .storeDurably(true)
                .build();
    }

    @Bean
    public Trigger buildCronTrigger(JobDetail job){
        return TriggerBuilder.newTrigger()
                .forJob(job)
                .withIdentity("Next_Reservation_Alarm_Trigger1")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 * * * * ?"))
                .build();
    }

    @Bean
    public Scheduler scheduler(Trigger trigger, JobDetail job, SchedulerFactoryBean factory) throws SchedulerException {
        Scheduler scheduler = factory.getScheduler();
        scheduler.start();
        return scheduler;
    }
}
