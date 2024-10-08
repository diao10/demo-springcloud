package com.diaoyn.provider.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.diaoyn.provider.entity.MyUser;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author diaoyn
 * @since 2024-06-06
 */
public interface IMyUserService extends IService<MyUser> {


    boolean exists(String userName, String password);
}
