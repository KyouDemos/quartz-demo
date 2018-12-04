package cn.ok.demos.quartzdemo.job.normal;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

/**
 * @author kyou on 2017/12/29 下午10:12
 */
@Slf4j
public class SimpleJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        log.debug("SimpleJob is executing ...");
    }
}
