package cn.jackbin.SimpleRecord.service.impl;

import cn.hutool.http.HttpUtil;
import cn.jackbin.SimpleRecord.dto.UrlObjectDto;
import cn.jackbin.SimpleRecord.service.ServerListService;
import cn.jackbin.SimpleRecord.service.TaskJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author: d6day
 * @date: 2023/4/02/002 13:09
 * @description:
 */
@Service
public class TaskJobImpl implements TaskJob {
    @Autowired
    private ServerListService service;

    @Override
    @Scheduled(cron = "0 5 0/1 * * ?")
    public void doJob() {
        UrlObjectDto urlObjectDto = service.saveHistoryList();
        if (null == urlObjectDto) {
            return;
        }
        // 发送微信公众号
        HttpUtil.get("localhost:9999/push/test");
    }
}
