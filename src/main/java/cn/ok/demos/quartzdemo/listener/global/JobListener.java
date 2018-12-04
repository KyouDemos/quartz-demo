package cn.ok.demos.quartzdemo.listener.global;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

/**
 * @author kyou on 2017/12/31 上午7:16
 */
@Slf4j
@Component
public class JobListener implements org.quartz.JobListener {
    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext jobExecutionContext) {
        String jobGroup = jobExecutionContext.getJobDetail().getKey().getGroup();
        String jobName = jobExecutionContext.getJobDetail().getKey().getName();
        log.debug("Job({}.{}) begins to Execute.", jobGroup, jobName);
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext jobExecutionContext) {
        String jobGroup = jobExecutionContext.getJobDetail().getKey().getGroup();
        String jobName = jobExecutionContext.getJobDetail().getKey().getName();
        log.debug("Job({}.{}) was Vetoed.", jobGroup, jobName);
    }

    @Override
    public void jobWasExecuted(JobExecutionContext jobExecutionContext, JobExecutionException e) {
        String jobGroup = jobExecutionContext.getJobDetail().getKey().getGroup();
        String jobName = jobExecutionContext.getJobDetail().getKey().getName();

        if (e == null) {
            log.debug("Job({}.{}) was Executed.", jobGroup, jobName);
        } else {
            log.error("Job({}.{}) was Executed, with Exception: {}.", jobGroup, jobName, e);
        }
    }
}
