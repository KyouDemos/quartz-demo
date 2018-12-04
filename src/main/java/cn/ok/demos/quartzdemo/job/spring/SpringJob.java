package cn.ok.demos.quartzdemo.job.spring;

import cn.ok.demos.quartzdemo.job.JobStyle;
import cn.ok.demos.quartzdemo.listener.spring.SpringJobListener;
import cn.ok.demos.quartzdemo.service.DemoService;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.matchers.KeyMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * 在 SpringBoot 框架中执行的任务
 *
 * @author kyou on 2017/12/30 下午1:51
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Slf4j
@Component
@DisallowConcurrentExecution
public class SpringJob extends QuartzJobBean implements JobStyle {
    private static final String JOB_GROUP = "SpringBootJobs";
    private static final String JOB_NAME = SpringJob.class.getSimpleName();

    @Autowired
    DemoService demoService;

    private String who;
    private String what;

    /**
     * Execute the actual job. The job data map will already have been
     * applied as bean property values by execute. The contract is
     * exactly the same as for the standard Quartz execute method.
     *
     * @param context JobExecutionContext
     * @see #execute
     */
    @Override
    protected void executeInternal(JobExecutionContext context) {
        demoService.speak(who, what);
    }

    /**
     * Liu Ying 不说话,因为 Trigger 中 JobDataMap 内的数据覆盖 JobDetail 中 JobDataMap 的数据.
     *
     * @return JobDetail
     */
    @Override
    public JobDetail getJobDetail() {
        return JobBuilder.newJob(SpringJob.class)
                .withIdentity(this.getClass().getSimpleName(), JOB_GROUP)
                .usingJobData("who", "Liu Ying")
                .usingJobData("what", "Mei You Jiu Suan le")
                .build();
    }

    /**
     * 预设每 3 秒执行一次,但由于 DemoServer 中 每次 Sleep 5 秒,任务实际是每 5 秒执行一次
     * 可以体现默认情况下,任务错过调度时间后,一旦处于就绪状态,将立刻执行.
     *
     * @return Trigger
     */
    @Override
    public Trigger getJobTrigger() {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(10).repeatForever();

        JobDataMap dataMap = new JobDataMap();
        dataMap.put("who", "Wang Da Na");
        dataMap.put("what", "Hai Yao Sha Zi Xing Che");

        return TriggerBuilder.newTrigger().forJob(getJobDetail())
                .withIdentity(this.getClass().getSimpleName() + "Trigger", JOB_GROUP)
                .usingJobData(dataMap)
                .startNow()
                .withSchedule(scheduleBuilder).build();
    }

    /**
     * 任务自带启动器,方便任务初始化
     * ConditionalOnProperty 注解实现任务启动的开关化.
     *
     * @param scheduler 虽然没有显示注入,但此处注入的是在 QuartzConfig 内的 quartzScheduler() 方法实例化的.
     * @return 本任务实例
     */
    @Bean
    @ConditionalOnProperty(value = {"Quartz.Jobs.SpringJob.Enable"})
    public SpringJob executeThisJob(Scheduler scheduler, SpringJobListener springJobListener) {

        JobDetail jobDetail = getJobDetail();
        Trigger jobTrigger = getJobTrigger();

        JobKey jobKey = jobDetail.getKey();
        TriggerKey triggerKey = jobTrigger.getKey();

        try {
            // 此处设定仅对当前 Job(JobKey 作为条件) 进行监听
            scheduler.getListenerManager().addJobListener(springJobListener, KeyMatcher.keyEquals(jobKey));
            if (scheduler.checkExists(jobKey)) {
                // 如果数据库中已经存在调度任务,则替换 Job 内容但是 Trigger 触发的频率以数据库的为准.
                jobTrigger = scheduler.getTrigger(triggerKey);
                scheduler.unscheduleJob(triggerKey);
                scheduler.scheduleJob(jobDetail, jobTrigger);
            } else {
                // 没有则新增调度任务
                scheduler.scheduleJob(jobDetail, jobTrigger);
            }

        } catch (SchedulerException e) {
            log.error("Execute Job({}.{}) Failed!", JOB_GROUP, this.getClass().getSimpleName());
            e.printStackTrace();
        }

        return new SpringJob();
    }
}
