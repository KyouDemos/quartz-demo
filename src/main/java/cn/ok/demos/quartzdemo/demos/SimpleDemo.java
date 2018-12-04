package cn.ok.demos.quartzdemo.demos;

import cn.ok.demos.quartzdemo.job.normal.SimpleJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * 一个最简单的定时任务示例
 *
 * @author kyou on 2017/12/29 下午10:11
 */
@SuppressWarnings("Duplicates")
public class SimpleDemo {
    public static void main(String[] args) throws SchedulerException {

        SchedulerFactory factory = new StdSchedulerFactory();
        Scheduler scheduler = factory.getScheduler();
        scheduler.start();

        Class<? extends Job> jobClass = SimpleJob.class;

        // define the job and tie it to our MyJob class
        JobDetail jobDetail = JobBuilder.newJob(jobClass)
                .withIdentity(jobClass.getSimpleName(), "DefaultJobGroup")
                .build();

        // Trigger the job to run now, and then repeat every 1 seconds
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "DefaultTriggerGroup")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(1)
                        .repeatForever())
                .build();

        // Tell quartz to schedule the job using our trigger
        scheduler.scheduleJob(jobDetail, trigger);
    }
}
