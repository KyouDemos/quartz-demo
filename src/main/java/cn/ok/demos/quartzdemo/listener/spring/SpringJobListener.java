package cn.ok.demos.quartzdemo.listener.spring;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.listeners.JobListenerSupport;
import org.springframework.stereotype.Component;

/**
 * @author kyou on 2017/12/31 上午10:58
 */
@Slf4j
@Component
public class SpringJobListener extends JobListenerSupport {

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        String jobGroup = context.getJobDetail().getKey().getGroup();
        String jobName = context.getJobDetail().getKey().getName();

        if (jobException == null) {
            log.trace("Job({}.{}) was executed successfully", jobGroup, jobName);
        } else {
            log.error("Job({}.{}) was executed failed with exception: {}", jobGroup, jobName, jobException);
        }
    }
}
