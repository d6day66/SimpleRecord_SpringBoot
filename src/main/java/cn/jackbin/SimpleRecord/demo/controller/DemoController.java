package cn.jackbin.SimpleRecord.demo.controller;

import cn.jackbin.SimpleRecord.demo.dto.DemoObject;
import cn.jackbin.SimpleRecord.demo.service.AbstractStrategyDemo;
import cn.jackbin.SimpleRecord.demo.service.DemoService;
import cn.jackbin.SimpleRecord.demo.service.HandlerFactory;
import cn.jackbin.SimpleRecord.demo.service.impl.StrategyDemoServiceImpl;
import cn.jackbin.SimpleRecord.service.ServerListService;
import my.json.service.MyService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: d6day
 * @date: 2023/2/09/009 9:35
 * @description:
 */
@RestController
@RequestMapping("/demo")
public class DemoController {
    @Resource
    private DemoService demoService;

    @Resource
    private StrategyDemoServiceImpl strategyDemoService;

    @Resource
    private ApplicationContext applicationContext;

    @Resource
    private MyService myService;
    @Resource
    private ServerListService service;

    @RequestMapping("test")
    public void test() {
        demoService.demoMethod();
        DemoObject p = new DemoObject("Mr.nobody", 18);
        // 调用服务方法
        String s = myService.objectToMyJson(p);
        System.out.println(s);
    }

    @RequestMapping("test2")
    public void test2() {
        strategyDemoService.calMethod(2);
    }

    @RequestMapping("test3")
    public void test3(@RequestParam(name = "type") Integer type) {
        AbstractStrategyDemo bean = HandlerFactory.getBean(type);
        bean.cal(type);
    }

    @RequestMapping("test4")
    public void test4(@RequestParam(name = "type") Integer type, @RequestParam(name = "name") String name) {
        AbstractStrategyDemo bean = applicationContext.getBean(name, AbstractStrategyDemo.class);
        bean.cal(type);
    }

    @RequestMapping("test5")
    public void test5() {
        service.saveHistoryList();
    }

}
