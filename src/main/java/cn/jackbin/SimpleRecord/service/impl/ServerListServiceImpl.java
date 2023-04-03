package cn.jackbin.SimpleRecord.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.jackbin.SimpleRecord.dto.UrlObjectDto;
import cn.jackbin.SimpleRecord.entity.CzFirstServerList;
import cn.jackbin.SimpleRecord.mapper.CzFirstServerListMapper;
import cn.jackbin.SimpleRecord.service.ServerListService;
import cn.jackbin.SimpleRecord.utils.HttpUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author: d6day
 * @date: 2023/4/02/002 12:37
 * @description:
 */
@Service
@Slf4j
public class ServerListServiceImpl extends ServiceImpl<CzFirstServerListMapper, CzFirstServerList> implements ServerListService {

    @Override
    public UrlObjectDto getCurrentServerList() {
        return HttpUtil.getCurrentServerList();
    }

    @Override
    public UrlObjectDto saveHistoryList() {
        UrlObjectDto currentServerList = HttpUtil.getCurrentServerList();
        CzFirstServerList one = this.getOne(Wrappers.<CzFirstServerList>lambdaQuery().eq(CzFirstServerList::getCurrentServer, 1));
        if (null != one && one.getCode() >= currentServerList.getId()) {
            log.info("未新增服务器");
            return null;
        }
        if (null != one) {
            one.setCurrentServer(0);
            this.updateById(one);
        }
        CzFirstServerList czFirstServerList = BeanUtil.copyProperties(currentServerList, CzFirstServerList.class);
        czFirstServerList.setId(null);
        czFirstServerList.setVersion(0);
        czFirstServerList.setCreateTime(new Date());
        czFirstServerList.setUpdateTime(new Date());
        czFirstServerList.setDelLog(0);
        czFirstServerList.setCurrentServer(1);
        czFirstServerList.setCode(currentServerList.getId());
        this.save(czFirstServerList);
        return currentServerList;
    }
}
