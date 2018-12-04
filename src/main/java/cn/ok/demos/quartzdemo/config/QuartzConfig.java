package cn.ok.demos.quartzdemo.config;

import cn.ok.demos.quartzdemo.listener.global.JobListener;
import cn.ok.demos.quartzdemo.listener.global.QuartzTriggerListener;
import cn.ok.demos.quartzdemo.listener.global.SchedulerListener;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.io.IOException;
import java.util.Properties;

/**
 * Quartz配置类
 *
 * @author kyou on 2017/12/30 下午1:05
 */
@Slf4j
@Configuration
public class QuartzConfig {
    private final QuartzJobFactory jobFactory;

    @Autowired
    public QuartzConfig(QuartzJobFactory jobFactory) {
        this.jobFactory = jobFactory;
    }

    @Bean(name = "QuartzDemoScheduler")
    public SchedulerFactoryBean quartzScheduler() {
        //Spring提供SchedulerFactoryBean为Scheduler提供配置信息,并被Spring容器管理其生命周期
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        //启动时更新己存在的Job，这样就不用每次修改targetObject后删除qrtz_job_details表对应记录了
        factory.setOverwriteExistingJobs(true);
        // 延时启动(秒)
        factory.setStartupDelay(2);
        //设置调度器自动运行
        factory.setAutoStartup(true);
        //设置quartz的配置文件
        factory.setQuartzProperties(quartzProperties());
        //设置自定义Job Factory，用于Spring管理Job bean
        factory.setJobFactory(jobFactory);

        return factory;
    }

    /**
     * 在使用 scheduler 调度任务之前先在这里实例化,添加监听,并启动.
     *
     * @param scheduler         调度器
     * @param jobListener       任务统一监听器
     * @param quartzTriggerListener   触发器统一监听器
     * @param schedulerListener 调度统一监听器
     * @return 调度器
     * @throws SchedulerException SchedulerException
     */
    @Bean
    public Scheduler scheduler(Scheduler scheduler, JobListener jobListener, QuartzTriggerListener quartzTriggerListener, SchedulerListener schedulerListener) throws
            SchedulerException {
        // 注入统一设置的任务监听器,默认对所有任务生效
        scheduler.getListenerManager().addJobListener(jobListener);
        // 注入统一设置的触发器的监听器,默认对所有触发器生效.
        scheduler.getListenerManager().addTriggerListener(quartzTriggerListener);
        // 注入统一设置的调度监听器,默认对所有触发器生效.
        scheduler.getListenerManager().addSchedulerListener(schedulerListener);

        return scheduler;
    }

    private Properties quartzProperties() {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        Properties properties = null;

        try {
            propertiesFactoryBean.afterPropertiesSet();
            properties = propertiesFactoryBean.getObject();
        } catch (IOException e) {
            log.warn("Cannot load quartz.properties.");
        }

        return properties;
    }
}
