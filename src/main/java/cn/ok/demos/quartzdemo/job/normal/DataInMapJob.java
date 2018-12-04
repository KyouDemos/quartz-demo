package cn.ok.demos.quartzdemo.job.normal;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

/**
 * 通过 JobDataMap 从外界获取数据的任务
 *
 * @author kyou on 2017/12/30 上午9:18
 */
@Data
@Slf4j
public class DataInMapJob implements Job {
    /**
     * 属性的 Get Set 方法通过 @Data 注解在编译时自动生成
     * 详细请查看 LomBok 用法
     */
    private String who;
    private String what;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {

        // getMergedJobDataMap方法可以获取来自 JobDetail 和 Trigger 的数据.
        JobDataMap dataMap = jobExecutionContext.getMergedJobDataMap();
        log.info("getMergedJobDataMap.who: {}", dataMap.getString("who"));

        // 单独从 JobDetail 中读取数据可采用如下方式
        JobDataMap dataMaoFromJobDetail = jobExecutionContext.getJobDetail().getJobDataMap();
        log.info("dataMaoFromJobDetail.who: {}", dataMaoFromJobDetail.getString("who"));

        /*
         * Quartz框架默认的 JobFactory 实现类在初始化job实例对象时
         * 会自动地调用这些 setter 方法，从而防止在调用执行方法时需要从 map 对象取指定的属性值。
         */
        log.info("{} says {}.", who, what);
    }
}
