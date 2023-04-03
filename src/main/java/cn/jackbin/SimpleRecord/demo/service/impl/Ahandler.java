package cn.jackbin.SimpleRecord.demo.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.jackbin.SimpleRecord.demo.service.AbstractStrategyDemo;
import cn.jackbin.SimpleRecord.demo.service.HandlerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author: d6day
 * @date: 2023/2/20/020 10:41
 * @description:
 */
@Component
public class Ahandler extends AbstractStrategyDemo {

    @Override
    public void cal(int type) {
        ArrayList<Object> objects = new ArrayList<>();
        objects.stream().forEach(a -> a.toString());
        String join = StrUtil.join("a", new Date());
        System.out.println("策略1");
    }

    @Override
    public void calB(int type) {
        super.calB(type);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        HandlerFactory.register(1, this);
    }
}
