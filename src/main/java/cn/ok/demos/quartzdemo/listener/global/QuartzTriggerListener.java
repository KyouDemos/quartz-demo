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
        log.trace("Trigger({}.{}) to fire Job({}.{}).", trigger.getKey().getGroup(),
                trigger.getKey().getName(), trigger.getJobKey().getGroup(),
                trigger.getJobKey().getName());
    }

    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext jobExecutionContext) {
        String vetoJobGroup = "SpringBootJobs";
        // 默认此处不拦截
        if (!vetoJobGroup.equals(trigger.getKey().getGroup())) {
            log.info("Trigger({}.{}) was vetoed to fire Job({}.{})!", trigger.getKey().getGroup(),
                    trigger.getKey().getName(), trigger.getJobKey().getGroup(),
                    trigger.getJobKey().getName());
            return true;
        }
        return false;
    }

    @Override
    public void triggerMisfired(Trigger trigger) {
        log.info("Trigger({}.{}) was Misfired Job({}.{})!", trigger.getKey().getGroup(),
                trigger.getKey().getName(), trigger.getJobKey().getGroup(),
                trigger.getJobKey().getName());
    }

    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext jobExecutionContext,
                                Trigger.CompletedExecutionInstruction completedExecution) {
        log.trace("Trigger({}.{}) was fired Job({}.{}), And completedExecution: {}",
                trigger.getKey().getGroup(), trigger.getKey().getName(),
                trigger.getJobKey().getGroup(), trigger.getJobKey().getName(), completedExecution);
    }
}
