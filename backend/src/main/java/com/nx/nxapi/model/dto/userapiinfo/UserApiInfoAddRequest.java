package com.nx.nxapi.model.dto.userapiinfo;

import lombok.Data;

import java.io.Serializable;

/**
 * 创建请求
 *
 * @author nx-xn2002
 */
@Data
public class UserApiInfoAddRequest implements Serializable {
    /**
     * 调用用户id
     */
    private Long userId;

    /**
     * 调用用户id
     */
    private Long apiInfoId;

    /**
     * 总调用次数
     */
    private Integer totalNum;

    /**
     * 剩余调用次数
     */
    private Integer leftNum;
}