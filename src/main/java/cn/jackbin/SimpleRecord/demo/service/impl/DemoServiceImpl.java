package cn.jackbin.SimpleRecord.demo.service.impl;

import cn.jackbin.SimpleRecord.demo.dto.DemoObject;
import cn.jackbin.SimpleRecord.demo.service.DemoService;
import cn.jackbin.SimpleRecord.demo.utils.ApplicationContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author: d6day
 * @date: 2023/2/09/009 9:41
 * @description:
 */
@Service
@Slf4j
public class DemoServiceImpl implements DemoService {

    @Override
    public void demoMethod() {
        // 手动注册bean
        DemoObject gh = new DemoObject("gh", 33);
        Object gh1 = ApplicationContextUtil.registerSingletonBean("gh", gh);
        System.out.println(gh1);
        log.info(String.valueOf(gh1));
    }

    @Override
    public void demoMethod(Object param) {

    }
}
