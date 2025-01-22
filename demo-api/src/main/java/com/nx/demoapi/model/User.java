package com.nx.demoapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * 用户
 *
 * @author nx-xn2002
 * @date 2025-01-20
 */
@Data
@Builder
@AllArgsConstructor
public class User {
    private String name;
}
