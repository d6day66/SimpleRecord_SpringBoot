package cn.jackbin.SimpleRecord.service.impl;

import cn.jackbin.SimpleRecord.bo.RecordDetailBO;
import cn.jackbin.SimpleRecord.service.RecordDetailFactory;
import cn.jackbin.SimpleRecord.service.RecordDetailHandler;
import cn.jackbin.SimpleRecord.service.RecordDetailService;
import cn.jackbin.SimpleRecord.vo.RecordDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: 支出记账策略
 * @date: 2021/10/8 20:58
 **/
@Component
public class ExpendRecordDetail implements RecordDetailHandler {
    @Autowired
    private RecordDetailFactory factory;

    @Autowired
    private RecordDetailService recordDetailService;

    @PostConstruct
    public void init(){
        factory.addHandler(EXPEND_TYPE, this);
    }

    @Override
    public void handle(Integer userId, RecordDetailBO bo) {
        recordDetailService.add(userId, bo.getTargetAccountId(), bo.getRecordBookId(), bo.getRecordTypeId(), bo.getRecordCategoryId(),
                bo.getAmount(), bo.getOccurTime(), bo.getTag(), bo.getRemark());
    }
}
