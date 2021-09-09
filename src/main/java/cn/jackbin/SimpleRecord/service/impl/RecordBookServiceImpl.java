package cn.jackbin.SimpleRecord.service.impl;

import cn.jackbin.SimpleRecord.constant.CommonConstants;
import cn.jackbin.SimpleRecord.constant.RecordConstant;
import cn.jackbin.SimpleRecord.entity.RecordBookDO;
import cn.jackbin.SimpleRecord.mapper.RecordBookMapper;
import cn.jackbin.SimpleRecord.service.RecordBookService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.service.impl
 * @date: 2021/7/13 22:04
 **/
@Service
public class RecordBookServiceImpl extends ServiceImpl<RecordBookMapper, RecordBookDO> implements RecordBookService {
    @Autowired
    private RecordBookMapper recordBookMapper;

    @Override
    public void add(Integer userId, String name, String remark, Integer orderNo) {
        RecordBookDO recordBookDO = new RecordBookDO();
        recordBookDO.setUserId(userId);
        recordBookDO.setName(name);
        recordBookDO.setRemark(remark);
        recordBookDO.setOrderNo(orderNo);
        recordBookDO.setStatus(CommonConstants.STATUS_NORMAL);
        recordBookMapper.insert(recordBookDO);
    }

    @Override
    public void getByName(String name) {
        QueryWrapper<RecordBookDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        recordBookMapper.selectOne(queryWrapper);
    }

    @Override
    public RecordBookDO getDefaultBook(Integer userId) {
        QueryWrapper<RecordBookDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_user_default", RecordConstant.USER_DEFAULT);
        queryWrapper.eq("user_id", userId);
        return recordBookMapper.selectOne(queryWrapper);
    }

    @Override
    public void edit(Long id, Integer userId, String name, String remark, Integer orderNo, Integer isUserDefault) {
        RecordBookDO recordBookDO = RecordBookDO.builder().id(id).userId(userId).
                name(name).remark(remark).orderNo(orderNo).isUserDefault(isUserDefault).build();
        recordBookMapper.updateById(recordBookDO);
    }
}
