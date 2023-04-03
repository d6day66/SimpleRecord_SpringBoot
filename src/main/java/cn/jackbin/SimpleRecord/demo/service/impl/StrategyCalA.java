package cn.jackbin.SimpleRecord.demo.service.impl;

import cn.jackbin.SimpleRecord.demo.service.StrategyDemoInterface;
import org.springframework.stereotype.Component;

/**
 * @author: d6day
 * @date: 2023/2/20/020 10:13
 * @description:
 */
@Component
public class StrategyCalA implements StrategyDemoInterface {
    static final int TYPE = 1;

    @Override
    public void cal() {
        System.out.println("策略1");
    }

    @Override
    public boolean isCurrent(int type) {
        return type == TYPE;
    }
}
