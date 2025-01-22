package com.nx.demoapisdk.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * 用户
 *
 * @author nx-xn2002
 */
@Data
@Builder
@AllArgsConstructor
public class User {
    /**
     * name
     */
    private String name;
}
