package com.nx.nxapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nx.nxapi.model.entity.UserApiInfo;

/**
 * user api info service
 *
 * @author nx-xn2002
 */
public interface UserApiInfoService extends IService<UserApiInfo> {
    /**
     * 校验
     *
     * @param userApiInfo user api info
     * @param add         add
     */
    void validUserApiInfo(UserApiInfo userApiInfo, boolean add);
}
