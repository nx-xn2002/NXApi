package com.nx.nxapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nx.nxapi.common.ErrorCode;
import com.nx.nxapi.exception.BusinessException;
import com.nx.nxapi.mapper.UserApiInfoMapper;
import com.nx.nxapi.model.entity.UserApiInfo;
import com.nx.nxapi.service.UserApiInfoService;
import org.springframework.stereotype.Service;

/**
 * user api info service impl
 *
 * @author nx-xn2002
 */
@Service
public class UserApiInfoServiceImpl extends ServiceImpl<UserApiInfoMapper, UserApiInfo>
        implements UserApiInfoService {

    @Override
    public void validUserApiInfo(UserApiInfo userApiInfo, boolean add) {
        if (userApiInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (add) {
            if (userApiInfo.getUserId() <= 0 || userApiInfo.getApiInfoId() <= 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口或用户不存在");
            }
        }
        if (userApiInfo.getLeftNum() < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "无效的调用次数");
        }
    }

    @Override
    public boolean invokeCount(long apiInfoId, long userId) {
        //校验
        if (apiInfoId <= 0 || userId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UpdateWrapper<UserApiInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("api_info_id", apiInfoId);
        updateWrapper.eq("user_id", userId);
        updateWrapper.gt("left_num", 0);
        updateWrapper.setSql("left_num = left_num - 1, total_num = total_num + 1");
        return this.update(updateWrapper);
    }
}




