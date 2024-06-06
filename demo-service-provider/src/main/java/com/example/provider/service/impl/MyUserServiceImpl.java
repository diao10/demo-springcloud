package com.example.provider.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.provider.entity.MyUser;
import com.example.provider.mapper.MyUserMapper;
import com.example.provider.service.IMyUserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author diaoyn
 * @since 2024-06-06
 */
@Service
public class MyUserServiceImpl extends ServiceImpl<MyUserMapper, MyUser> implements IMyUserService {


    @Override
    public boolean exists(String userName, String password) {
        LambdaQueryChainWrapper<MyUser> wrapper = lambdaQuery();
        wrapper.eq(MyUser::getUserName, userName);
        wrapper.eq(MyUser::getPassword, password);
        return wrapper.exists();
    }

}
