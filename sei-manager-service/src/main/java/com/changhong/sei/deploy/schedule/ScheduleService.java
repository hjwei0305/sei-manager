package com.changhong.sei.deploy.schedule;

import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-12-08 18:07
 */
public class ScheduleService {

    /**
     * 0/5 * * * * ?  每隔5秒触发一次
     * 0 0/2 * * * ?  每隔2分钟触发一次
     * 0 15 1 * * ?   每天1:15触发
     */
    @Scheduled(cron = "0 0/2 * * * ?")
    public void cron() {
        System.out.println("cron>>" + new Date());
    }
}
