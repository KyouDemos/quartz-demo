package cn.ok.demos.quartzdemo.demos;

import cn.ok.demos.quartzdemo.job.normal.DataInMapJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * 通过 JobDataMap 向 Job 中传递数据
 *
 * @author kyou on 2017/12/30 上午9:14
 */
@SuppressWarnings("Duplicates")
public class DataFromJobDetail {
    public static void main(String[] args) throws SchedulerException {
        SchedulerFactory factory = new StdSchedulerFactory();
        Scheduler scheduler = factory.getScheduler();
        scheduler.start();

        Class<? extends Job> jobClass = DataInMapJob.class;

        // define the job and tie it to our MyJob class
        JobDetail jobDetail = JobBuilder.newJob(jobClass)
                .withIdentity(jobClass.getSimpleName(), "DefaultJobGroup")
                .usingJobData("who", "A")
                .usingJobData("what", "aaa")
                .build();

        // Trigger the job to run now, and then repeat every 40 seconds
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("DataFromJobDetailTrigger1", "DefaultTriggerGroup")
                // 此处会覆盖 JobDetail 中预设的 what 属性
                .usingJobData("what", "not aaa")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(1)
                        .repeatForever())
                .build();

        // Tell quartz to schedule the job using our trigger
        scheduler.scheduleJob(jobDetail, trigger);
    }
}
