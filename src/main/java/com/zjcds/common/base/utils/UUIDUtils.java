package com.zjcds.common.base.utils;

import java.util.UUID;

/**
 * created date：2017-08-07
 * @author niezhegang
 */
public abstract class UUIDUtils {
    /**
     * 生成32字符的随机uuid
     * @return
     */
    public static String randomUUID(){
        UUID uuid = UUID.randomUUID();
        long mostSigBits = uuid.getMostSignificantBits();
        long leastSigBits = uuid.getLeastSignificantBits();
        return (digits(mostSigBits >> 32, 8) +
                digits(mostSigBits >> 16, 4) +
                digits(mostSigBits, 4)  +
                digits(leastSigBits >> 48, 4) +
                digits(leastSigBits, 12));
    }

    private static String digits(long val, int digits) {
        long hi = 1L << (digits * 4);
        return Long.toHexString(hi | (val & (hi - 1))).substring(1);
    }

}
