package cn.jackbin.SimpleRecord.service;

import cn.jackbin.SimpleRecord.dto.UrlObjectDto;
import cn.jackbin.SimpleRecord.entity.CzFirstServerList;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author: d6day
 * @date: 2023/4/02/002 12:28
 * @description:
 */
public interface ServerListService extends IService<CzFirstServerList> {
    UrlObjectDto getCurrentServerList();

    UrlObjectDto saveHistoryList();
}
