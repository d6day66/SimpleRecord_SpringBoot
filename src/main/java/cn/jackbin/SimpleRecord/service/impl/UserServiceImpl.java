package cn.jackbin.SimpleRecord.service.impl;

import cn.jackbin.SimpleRecord.entity.UserDO;
import cn.jackbin.SimpleRecord.entity.UserGroupDO;
import cn.jackbin.SimpleRecord.mapper.UserMapper;
import cn.jackbin.SimpleRecord.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jackbin
 * @since 2020-07-21
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {


    @Override
    public UserDO getUserByUserName(String userName) {
        QueryWrapper<UserDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",userName);
        UserDO userDO = getOne(queryWrapper);
        return userDO;
    }
}
