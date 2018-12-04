package cn.ok.demos.quartzdemo.util;

import cn.ok.demos.quartzdemo.listener.spring.SpringJobListener;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.matchers.KeyMatcher;
import org.springframework.stereotype.Component;

/**
 * @author kyou on 2017/12/31 下午4:29
 */
@Slf4j
@Component
public class JobUtil {
    public void executeThisJob(Scheduler scheduler, JobDetail jobDetail, Trigger jobTrigger, SpringJobListener
            springJobListener) {

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
            log.error("Execute Job({}) Failed!", this.getClass().getSimpleName());
            e.printStackTrace();
        }
    }
}
