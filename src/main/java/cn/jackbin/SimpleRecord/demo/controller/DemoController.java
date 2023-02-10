package cn.jackbin.SimpleRecord.demo.controller;

import cn.jackbin.SimpleRecord.demo.dto.DemoObject;
import cn.jackbin.SimpleRecord.demo.service.DemoService;
import my.json.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    MyService myService;

    @RequestMapping("test")
    public void test() {
        demoService.demoMethod();
        DemoObject p = new DemoObject("Mr.nobody", 18);
        // 调用服务方法
        String s = myService.objectToMyJson(p);
        System.out.println(s);
    }

}
