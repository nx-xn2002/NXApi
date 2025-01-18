package com.nx.nxapi.model.dto.user;

import java.io.Serializable;

import lombok.Data;

/**
 * 用户创建请求
 *
 * @author nx-xn2002
 * @date 2025-01-18
 */
@Data
public class UserAddRequest implements Serializable {

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 用户角色: user, admin
     */
    private String userRole;
}