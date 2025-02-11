package com.nx.nxapi.model.dto.apiinfo;

import lombok.Data;

import java.io.Serializable;

/**
 * 调用请求
 *
 * @author nx-xn2002
 */
@Data
public class ApiInfoInvokeRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 用户请求参数
     */
    private String userRequestParams;
}