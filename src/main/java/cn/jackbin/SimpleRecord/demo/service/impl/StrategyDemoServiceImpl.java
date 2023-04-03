package cn.jackbin.SimpleRecord.demo.service.impl;

import cn.jackbin.SimpleRecord.demo.service.StrategyDemoInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: d6day
 * @date: 2023/2/20/020 10:19
 * @description:
 */
@Service
public class StrategyDemoServiceImpl {
    @Autowired
    List<StrategyDemoInterface> strategyDemoInterfaces;

    public void calMethod(int type) {
        StrategyDemoInterface strategyDemoInterface = strategyDemoInterfaces.stream().filter(l -> l.isCurrent(type)).findFirst().get();
        strategyDemoInterface.cal();

    }
}
