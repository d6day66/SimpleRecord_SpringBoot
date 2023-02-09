package cn.jackbin.SimpleRecord.demo.controller;

import cn.jackbin.SimpleRecord.demo.service.DemoService;
import org.springframework.web.bind.annotation.RequestMapping;
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
    DemoService demoService;

    @RequestMapping("test")
    public void test() {
        demoService.demoMethod();
    }
}
