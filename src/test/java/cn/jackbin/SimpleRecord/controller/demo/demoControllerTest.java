package cn.jackbin.SimpleRecord.controller.demo;

import cn.jackbin.SimpleRecord.common.config.flow.YourClass;
import cn.jackbin.SimpleRecord.mapper.MenuMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: d6day
 * @date: 2022/12/08/008 11:10
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class demoControllerTest {

    @Autowired
    private YourClass yourClass;

    @Autowired
    private MenuMapper menuMapper;

    @Test
    public void queryPermissionByUserId() {
        yourClass.testConfig();
    }

    @Test
    public void menuList() {
        menuMapper.selectList(new QueryWrapper<>());
    }
}