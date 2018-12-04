package cn.ok.demos.quartzdemo.job;

import org.quartz.JobDetail;
import org.quartz.Trigger;

/**
 * @author kyou on 2017/12/31 上午6:47
 */
public interface JobStyle {
    /**
     * 强制在任务对象中设定任务的 JobDetail.
     *
     * @return 当前任务的 JobDetail
     */
    JobDetail getJobDetail();

    /**
     * 强制在任务对象中设定任务的 Trigger.
     *
     * @return 当前任务的 Trigger
     */
    Trigger getJobTrigger();
}
