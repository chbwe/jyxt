package com.ald.finance.common.utils;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * Created by liang3.zhang on 2018/5/8.
 */
public final class RandomUtil {

    private static final int DEF_COUNT = 3;

    public static final int VCODE_COUNT = 6;

    private RandomUtil() {
    }

    /**
     * Generates a password.
     *
     * @return the generated password
     */
    public static String generatePassword() {
        String password = RandomStringUtils.randomAlphanumeric(DEF_COUNT);
        return password.toLowerCase() + RandomStringUtils.randomNumeric(DEF_COUNT);
    }

    /**
     * 创建一个短信验证码
     *
     * @return the generated reset key
     */
    public static String generateVCode() {
        return RandomStringUtils.randomNumeric(VCODE_COUNT);
    }
}

