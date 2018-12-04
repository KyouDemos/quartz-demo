package cn.ok.demos.quartzdemo.listener.global;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.listeners.SchedulerListenerSupport;
import org.springframework.stereotype.Component;

/**
 * @author kyou on 2017/12/31 上午9:30
 */
@Slf4j
@Component
public class SchedulerListener extends SchedulerListenerSupport {

    @Override
    public void jobScheduled(Trigger trigger) {
        String triggerGroup = trigger.getKey().getGroup();
        String triggerName = trigger.getKey().getName();

        String jobGroup = trigger.getJobKey().getGroup();
        String jobName = trigger.getJobKey().getName();

        log.debug("Job({}.{}), Trigger({}.{}) was scheduled.", jobGroup, jobName, triggerGroup, triggerName);
    }

    @Override
    public void jobUnscheduled(TriggerKey triggerKey) {
        String triggerGroup = triggerKey.getGroup();
        String triggerName = triggerKey.getName();

        log.debug("Trigger({}.{}) was Unscheduled.", triggerGroup, triggerName);
    }

    @Override
    public void triggerFinalized(Trigger trigger) {
        String triggerGroup = trigger.getKey().getGroup();
        String triggerName = trigger.getKey().getName();

        String jobGroup = trigger.getJobKey().getGroup();
        String jobName = trigger.getJobKey().getName();

        log.debug("rigger ({}.{}) was Finalized.", jobGroup, jobName, triggerGroup, triggerName);
    }

    @Override
    public void triggerPaused(TriggerKey triggerKey) {
        String triggerGroup = triggerKey.getGroup();
        String triggerName = triggerKey.getName();

        log.debug("Trigger({}.{}) was Unscheduled.", triggerGroup, triggerName);
    }

    @Override
    public void triggersPaused(String s) {
        log.debug("triggersPaused, param s: {}.", s);
    }

    @Override
    public void triggerResumed(TriggerKey triggerKey) {
        String triggerGroup = triggerKey.getGroup();
        String triggerName = triggerKey.getName();

        log.debug("Trigger({}.{}) was Unscheduled.", triggerGroup, triggerName);
    }

    @Override
    public void triggersResumed(String s) {
        log.debug("triggersResumed, param s: {}.", s);
    }

    @Override
    public void jobAdded(JobDetail jobDetail) {
        String jobGroup = jobDetail.getKey().getGroup();
        String jobName = jobDetail.getKey().getName();

        log.debug("Job({}.{}) was Added.", jobGroup, jobName);
    }

    @Override
    public void jobDeleted(JobKey jobKey) {
        String jobGroup = jobKey.getGroup();
        String jobName = jobKey.getName();

        log.debug("Job({}.{}) was Added.", jobGroup, jobName);
    }

    @Override
    public void jobPaused(JobKey jobKey) {
        String jobGroup = jobKey.getGroup();
        String jobName = jobKey.getName();

        log.debug("Job({}.{}) was Added.", jobGroup, jobName);
    }

    @Override
    public void jobsPaused(String s) {
        log.debug("jobsPaused, param s: {}.", s);
    }

    @Override
    public void jobResumed(JobKey jobKey) {
        String jobGroup = jobKey.getGroup();
        String jobName = jobKey.getName();

        log.debug("Job({}.{}) was Added.", jobGroup, jobName);
    }

    @Override
    public void jobsResumed(String s) {
        log.debug("jobsResumed, param s: {}.", s);
    }

    @Override
    public void schedulerInStandbyMode() {
        log.debug("schedulerInStandbyMode.");
    }

    @Override
    public void schedulerStarted() {
        log.debug("schedulerStarted.");
    }

    @Override
    public void schedulerStarting() {
        log.debug("schedulerStarting.");
    }

    @Override
    public void schedulerShutdown() {
        log.debug("schedulerShutdown.");
    }

    @Override
    public void schedulerShuttingdown() {
        log.debug("schedulerShuttingDown.");
    }

    @Override
    public void schedulingDataCleared() {
        log.debug("schedulingDataCleared.");
    }
}
