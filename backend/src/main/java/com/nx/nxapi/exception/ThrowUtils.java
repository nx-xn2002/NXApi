package com.nx.nxapi.exception;

import com.nx.nxapi.common.ErrorCode;

/**
 * 抛异常工具类
 *
 * @author nx-xn2002
 * @date 2025-01-11
 */
public class ThrowUtils {

    /**
     * 条件成立则抛异常
     *
     * @param condition        condition
     * @param runtimeException runtime exception
     */
    public static void throwIf(boolean condition, RuntimeException runtimeException) {
        if (condition) {
            throw runtimeException;
        }
    }

    /**
     * 条件成立则抛异常
     *
     * @param condition condition
     * @param errorCode error code
     */
    public static void throwIf(boolean condition, ErrorCode errorCode) {
        throwIf(condition, new BusinessException(errorCode));
    }

    /**
     * 条件成立则抛异常
     *
     * @param condition condition
     * @param errorCode error code
     * @param message   message
     */
    public static void throwIf(boolean condition, ErrorCode errorCode, String message) {
        throwIf(condition, new BusinessException(errorCode, message));
    }
}
