package cn.jackbin.SimpleRecord.controller.demo;

import cn.jackbin.SimpleRecord.dto.UrlObjectDto;
import cn.jackbin.SimpleRecord.service.ServerListService;
import cn.jackbin.SimpleRecord.service.impl.DictServiceImpl;
import cn.jackbin.SimpleRecord.vo.Result;
import com.github.xiaoymin.knife4j.annotations.Ignore;
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

    @Autowired
    private ServerListService listService;

    @GetMapping
    public Result<?> getAnswer(String question) {
        String demo = dictService.demo(question);
        return Result.success(demo);
    }

    @Ignore
    @GetMapping("/getServerList")
    public Result<?> getServerList() {
        UrlObjectDto one = listService.getCurrentServerList();
        return Result.success(one);
    }


}
