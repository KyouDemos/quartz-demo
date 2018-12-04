package cn.ok.demos.quartzdemo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author kyou on 2017/12/30 下午3:50
 */
@Slf4j
@Component
public class SpeakService {
    public void speak(String who, String what) {
        log.info("{} says: {}.", who, what);

        try {
            // Job类添加 @DisallowConcurrentExecution 注解后,不可并发执行.
            Thread.sleep(3 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
