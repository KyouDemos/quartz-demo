package cn.ok.demos.quartzdemo.demos;

import cn.ok.demos.quartzdemo.job.normal.DataInMapJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * 多个 Trigger 启动同一个 Job, 且数据由 Trigger 传入.
 *
 * @author kyou on 2017/12/30 上午10:05ß
 */

@SuppressWarnings("Duplicates")
public class MultiTriggerOneJob {
    public static void main(String[] args) throws SchedulerException {
        SchedulerFactory factory = new StdSchedulerFactory();
        Scheduler scheduler = factory.getScheduler();
        scheduler.start();

        Class<? extends Job> jobClass = DataInMapJob.class;

        // define the job and tie it to our MyJob class
        JobDetail jobDetail = JobBuilder.newJob(jobClass)
                .withIdentity(jobClass.getSimpleName(), "DefaultJobGroup1")
                .build();

        // 准备数据
        JobDataMap dataMap = new JobDataMap();
        dataMap.put("who", "AA");
        dataMap.put("what", "Hello World.");

        // Trigger the job to run now, and then repeat every 10 seconds
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("MultiTriggerOneJob", "DefaultTriggerGroup")
                .forJob(jobDetail)
                .usingJobData(dataMap)
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(10)
                        .repeatForever())
                .build();

        // 重新准备数据
        dataMap = new JobDataMap();
        dataMap.put("who", "BB");
        dataMap.put("what", "Hello.");

        // Trigger the job to run now, and then repeat every 2 seconds
        Trigger trigger2 = TriggerBuilder.newTrigger()
                .withIdentity("MultiTriggerOneJobTrigger", "DefaultTriggerGroup")
                .forJob(jobDetail)
                .usingJobData(dataMap)
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule("0/2 * * * * ? *"))
                .build();

        // 第一个 Trigger 启动时,需要指定 Job, 否则会提示 Job 不存在.
        scheduler.scheduleJob(jobDetail, trigger);
        scheduler.scheduleJob(trigger2);
    }
}
