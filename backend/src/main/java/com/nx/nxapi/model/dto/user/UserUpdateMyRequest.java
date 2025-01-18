package com.nx.nxapi.model.dto.user;

import java.io.Serializable;

import lombok.Data;

/**
 * 用户更新个人信息请求
 *
 * @author nx-xn2002
 * @date 2025-01-18
 */
@Data
public class UserUpdateMyRequest implements Serializable {

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 简介
     */
    private String userProfile;
}