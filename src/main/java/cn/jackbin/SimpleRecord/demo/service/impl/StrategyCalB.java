package cn.jackbin.SimpleRecord.demo.service.impl;

import cn.jackbin.SimpleRecord.demo.service.StrategyDemoInterface;
import org.springframework.stereotype.Component;

/**
 * @author: d6day
 * @date: 2023/2/20/020 10:13
 * @description:
 */
@Component
public class StrategyCalB implements StrategyDemoInterface {
    static final int TYPE = 2;

    @Override
    public void cal() {
        System.out.println("策略2");
    }

    @Override
    public boolean isCurrent(int type) {
        return type == TYPE;
    }
}
