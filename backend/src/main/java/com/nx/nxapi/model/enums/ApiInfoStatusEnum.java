package com.nx.nxapi.model.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 接口状态枚举
 *
 * @author nx-xn2002
 */
@Getter
public enum ApiInfoStatusEnum {

    /**
     * user
     */
    OFFLINE("关闭", 0),
    ONLINE("上线", 1);

    private final String text;

    @Getter
    private final Integer value;

    ApiInfoStatusEnum(String text, Integer value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 获取值列表
     *
     * @return {@link List }<{@link Integer }>
     */
    public static List<Integer> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }
}
