package cn.ok.demos.quartzdemo.listener.global;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;
import org.springframework.stereotype.Component;

/**
 * @author kyou on 2017/12/31 上午8:36
 */
@Slf4j
@Component
public class QuartzTriggerListener implements TriggerListener {

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext jobExecutionContext) {
        String triggerGroup = trigger.getKey().getGroup();
        String triggerName = trigger.getKey().getName();

        String jobGroup = trigger.getJobKey().getGroup();
        String jobName = trigger.getJobKey().getName();

        log.trace("Trigger({}.{}) to fire Job({}.{}).", triggerGroup, triggerName, jobGroup, jobName);
    }

    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext jobExecutionContext) {
        String triggerGroup = trigger.getKey().getGroup();
        String triggerName = trigger.getKey().getName();

        String jobGroup = trigger.getJobKey().getGroup();
        String jobName = trigger.getJobKey().getName();

        String vetoJobGroup = "SpringBootJobs";
        // 默认此处不拦截
        if (!vetoJobGroup.equals(trigger.getKey().getGroup())) {
            log.info("Trigger({}.{}) was vetoed to fire Job({}.{})!", triggerGroup, triggerName, jobGroup, jobName);
            return true;
        }
        return false;
    }

    @Override
    public void triggerMisfired(Trigger trigger) {
        String triggerGroup = trigger.getKey().getGroup();
        String triggerName = trigger.getKey().getName();

        String jobGroup = trigger.getJobKey().getGroup();
        String jobName = trigger.getJobKey().getName();

        log.info("Trigger({}.{}) was Misfired Job({}.{})!", triggerGroup, triggerName, jobGroup, jobName);
    }

    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext jobExecutionContext,
                                Trigger.CompletedExecutionInstruction completedExecution) {

        String triggerGroup = trigger.getKey().getGroup();
        String triggerName = trigger.getKey().getName();

        String jobGroup = trigger.getJobKey().getGroup();
        String jobName = trigger.getJobKey().getName();

        log.trace("Trigger({}.{}) was fired Job({}.{}), And completedExecution: {}", triggerGroup, triggerName,
                jobGroup, jobName, completedExecution);
    }
}
