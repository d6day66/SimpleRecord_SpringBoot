package cn.jackbin.SimpleRecord.demo.service.impl;

import cn.jackbin.SimpleRecord.demo.service.AbstractStrategyDemo;
import cn.jackbin.SimpleRecord.demo.service.HandlerFactory;
import org.springframework.stereotype.Component;

/**
 * @author: d6day
 * @date: 2023/2/20/020 10:41
 * @description:
 */
@Component
public class Bhandler extends AbstractStrategyDemo {
    @Override
    public void cal(int type) {
        System.out.println("策略2");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        HandlerFactory.register(2, this);
    }
}
