package cn.jackbin.SimpleRecord.controller.demo;

import cn.jackbin.SimpleRecord.service.impl.DictServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: d6day
 * @date: 2022/12/22/022 11:20
 * @description:
 */
@RestController
@RequestMapping("/get")
public class BaiduDemoController {
    @Autowired
    private DictServiceImpl dictService;

    @GetMapping
    public String getAnswer(String question) {
        return dictService.demo(question);
    }

}
