package com.nx.nxapi.common;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * ID 请求封装
 *
 * @author nx-xn2002
 */
@Data
public class IdRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    @Serial
    private static final long serialVersionUID = 1L;
}