package cn.jackbin.SimpleRecord.demo.service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: d6day
 * @date: 2023/2/20/020 10:45
 * @description:
 */
public class HandlerFactory {
    private static Map<Integer, AbstractStrategyDemo> map = new HashMap<>();

    public static AbstractStrategyDemo getBean(int type) {
        return map.get(type);
    }

    public static void register(int type, AbstractStrategyDemo handler) {
        map.put(type, handler);
    }
}
